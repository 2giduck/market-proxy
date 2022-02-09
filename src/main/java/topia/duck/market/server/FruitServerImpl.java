package topia.duck.market.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FruitServerImpl implements FruitServer{

    private static final Logger logger = LoggerFactory.getLogger(FruitServerImpl.class);

    @Value("${service.host.fruit}")
    private String FRUIT_HOST;

    private final String HTTP_URL="http://";
    private final String GET_TOKEN_END_POINT="/token";
    private final String GET_FRUIT_END_POINT="/product";
    private final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private final String NAME_QUERY_KEY="name";

    private static String accessToken;

    public void getAccessToken() {
        String token = WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(GET_TOKEN_END_POINT)
                .retrieve()
                .bodyToFlux(Token.class).blockFirst().getAccessToken();

        logger.info("accessToken = "+accessToken);
        accessToken = token;
    }

    @Override
    public String[] getFruitList() {
        String fruits = WebClient.create(HTTP_URL + FRUIT_HOST)
                .get()
                .uri(GET_FRUIT_END_POINT)
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst();

        return fruits.substring(1, fruits.length()-1).replaceAll("\"","").split(",");
    }


    @Override
    public Product getFruit(String name) {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_FRUIT_END_POINT)
                        .queryParam(NAME_QUERY_KEY, name)
                        .build()
                )
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .exchangeToMono(resp->{
                  if(resp.statusCode().is4xxClientError())
                      throw new IllegalStateException("지정된 이름에 해당하는 정보가 없습니다.");
                  else
                      return resp.bodyToMono(Product.class);
                }).block();
    }
}

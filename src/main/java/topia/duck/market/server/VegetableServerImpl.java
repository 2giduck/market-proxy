package topia.duck.market.server;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

@Component
public class VegetableServerImpl implements VegetableServer {
    private static final Logger logger = LoggerFactory.getLogger(FruitServerImpl.class);

    @Value("${service.host.vegetable}")
    private String VEGETABLE_HOST;

    private final String HTTP_URL="http://";
    private final String GET_TOKEN_END_POINT="/token";
    private final String GET_VEGETABLE_END_POINT="/item";
    private final String SET_COOKIE_HEADER_KEY = "Set-Cookie";
    private final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private final String NAME_QUERY_KEY="name";

    private static String accessToken;

    @Override
    public void getAccessToken() {
        if(accessToken==null){
            ClientResponse resp = WebClient.create(HTTP_URL+VEGETABLE_HOST)
                    .get()
                    .uri(GET_TOKEN_END_POINT)
                    .exchange().block();

            try{
                String token = resp.headers().asHttpHeaders()
                        .getFirst(SET_COOKIE_HEADER_KEY)
                        .replaceFirst("Authorization=","")
                        .replace("; Path=/","");

                accessToken = token;
                logger.info("vegetable_access_token = "+accessToken);
            }catch(Exception e){
                logger.error("API 서버 에러 | "+resp.statusCode());
            }
        }
    }

    @Override
    public Flux<String> getVegetableList() {
        return WebClient.create(HTTP_URL + VEGETABLE_HOST)
                .get()
                .uri(GET_VEGETABLE_END_POINT)
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @Override
    public Flux<Product> getVegetable(String name) {
        return WebClient.create(HTTP_URL+VEGETABLE_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_VEGETABLE_END_POINT)
                        .queryParam(NAME_QUERY_KEY, name)
                        .build()
                )
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .exchangeToFlux(resp->{
                    if(resp.statusCode().is4xxClientError())
                        throw new IllegalStateException("지정된 이름에 해당하는 정보가 없습니다.");
                    else
                        return resp.bodyToFlux(Product.class);
                });
    }
}

package topia.duck.market.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

@Component
public class FruitServerImpl implements FruitServer{

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

        accessToken = token;
    }

    @Override
    public Flux<String> getFruitList() {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(GET_FRUIT_END_POINT)
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @Override
    public Flux<Product> getFruit(String name) {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_FRUIT_END_POINT)
                        .queryParam(NAME_QUERY_KEY, name)
                        .build()
                )
                .header(AUTHORIZATION_HEADER_KEY, accessToken)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}

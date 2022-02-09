package topia.duck.market.server;

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

    @Override
    public Flux<Token> getAccessToken() {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(GET_TOKEN_END_POINT)
                .retrieve()
                .bodyToFlux(Token.class);
    }

    @Override
    public Flux<String> getFruitList(String token) {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(GET_FRUIT_END_POINT)
                .header(AUTHORIZATION_HEADER_KEY, token)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @Override
    public Flux<Product> getFruit(String token, String name) {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_FRUIT_END_POINT)
                        .queryParam(NAME_QUERY_KEY, name)
                        .build()
                )
                .header(AUTHORIZATION_HEADER_KEY, token)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}

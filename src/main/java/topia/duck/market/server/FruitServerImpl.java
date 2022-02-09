package topia.duck.market.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

import java.util.List;

@Component
public class FruitServerImpl implements FruitServer{

    @Value("${service.host.fruit}")
    private String FRUIT_HOST;

    private final String HTTP_URL="http://";
    private final String GET_TOKEN_END_POINT="/token";
    private final String GET_FRUIT_END_POINT="/product";

    @Override
    public Flux<Token> getAccessToken() {
        return WebClient.create(HTTP_URL+FRUIT_HOST)
                .get()
                .uri(GET_TOKEN_END_POINT)
                .retrieve()
                .bodyToFlux(Token.class);
    }

    @Override
    public List<String> getFruitList() {
        return null;
    }

    @Override
    public Product getFruit(String name) {
        return null;
    }
}

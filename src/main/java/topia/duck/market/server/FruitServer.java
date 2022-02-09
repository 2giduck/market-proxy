package topia.duck.market.server;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

@Component
public interface FruitServer {
    public Flux<Token> getAccessToken();

    public Flux<String> getFruitList(String token);   // 과일 이름 리스트 가져오기

    public Flux<Product> getFruit(String token, String name); // 과일 이름으로 가격 찾기
}

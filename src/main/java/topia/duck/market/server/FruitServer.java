package topia.duck.market.server;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

import java.util.List;

@Component
public interface FruitServer {
    public Flux<Token> getAccessToken();

    public List<String> getFruitList();   // 과일 이름 리스트 가져오기

    public Product getFruit(String name); // 과일 이름으로 가격 찾기
}

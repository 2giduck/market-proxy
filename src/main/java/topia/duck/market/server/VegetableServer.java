package topia.duck.market.server;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;

@Component
public interface VegetableServer {
    void getAccessToken();

    Flux<String> getVegetableList();   // 채소 이름 리스트 가져오기

    Flux<Product> getVegetable(String name); // 채소 이름으로 가격 찾기
}

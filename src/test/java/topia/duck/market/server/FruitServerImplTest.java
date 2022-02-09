package topia.duck.market.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FruitServerImplTest {
    @Autowired
    private FruitServerImpl fruitServer;

    @Test
    @DisplayName("과일 토큰 가져오기 테스트")
    public void 과일_토큰_가져오기(){
        //given

        //when

        //then
        assertDoesNotThrow(()->fruitServer.getAccessToken());
    }

    @Test
    @DisplayName("과일 이름 리스트 가져오기 테스트")
    public void 과일_이름_리스트_가져오기(){
        //given
        fruitServer.getAccessToken();

        //when
        Flux<String> fruitList = fruitServer.getFruitList();

        //then
        assertThat(fruitList.blockFirst()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("과일 가격 가져오기 테스트")
    public void 과일_가격_가져오기(){
        //given
        fruitServer.getAccessToken();
        String name = "사과";

        //when
        Flux<Product> product = fruitServer.getFruit(name);

        //then
        assertThat(product.blockFirst().getName()).isEqualTo(name);
    }

}
package topia.duck.market.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.domain.Token;

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
        Flux<Token> tokenFlux = fruitServer.getAccessToken();

        //then
        assertThat(tokenFlux.blockFirst()).isNotEqualTo(null);
        System.out.println("token = " + tokenFlux.blockFirst().getAccessToken());
    }

    @Test
    @DisplayName("과일 이름 리스트 가져오기 테스트")
    public void 과일_이름_리스트_가져오기(){
        //given
        String token = fruitServer.getAccessToken().blockFirst().getAccessToken();

        //when
        Flux<String> fruitList = fruitServer.getFruitList(token);

        //then
        assertThat(fruitList).isNotEqualTo(null);
    }

    @Test
    @DisplayName("과일 가격 가져오기 테스트")
    public void 과일_가격_가져오기(){
        //given
        String token = fruitServer.getAccessToken().blockFirst().getAccessToken();
        String name = "사과";

        //when
        Flux<Product> fruitFlux = fruitServer.getFruit(token, name);

        //then
        assertThat(fruitFlux.blockFirst()).isNotEqualTo(null);
        System.out.println("fruit = " + fruitFlux.blockFirst().getPrice());
    }

}
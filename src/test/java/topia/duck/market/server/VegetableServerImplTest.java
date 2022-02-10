package topia.duck.market.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class VegetableServerImplTest {
    @Autowired
    private VegetableServer vegetableServer;

    @Test
    @DisplayName("채소 토큰 가져오기 테스트")
    public void 채소_토큰_가져오기(){
        //given

        //when

        //then
        assertDoesNotThrow(()->vegetableServer.getAccessToken());
    }

    @Test
    @DisplayName("채소 이름 리스트 가져오기 테스트")
    public void 채소_이름_리스트_가져오기(){
        //given
        vegetableServer.getAccessToken();

        //when
        Flux<String> vegetableList = vegetableServer.getVegetableList();

        //then
        assertThat(vegetableList.blockFirst()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("채소 가격 가져오기 테스트")
    public void 채소_가격_가져오기(){
        //given
        vegetableServer.getAccessToken();
        String name = "치커리";

        //when
        Flux<Product> vegetable = vegetableServer.getVegetable(name);

        //then
        assertThat(vegetable.blockFirst().getName()).isEqualTo(name);
    }
}

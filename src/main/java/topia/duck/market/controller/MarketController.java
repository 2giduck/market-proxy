package topia.duck.market.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import topia.duck.market.server.FruitServer;
import topia.duck.market.server.VegetableServer;


@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class MarketController {

    private final FruitServer fruitServer;
    private final VegetableServer vegetableServer;

    @GetMapping("/fruit")
    public Flux<?> getFruitItems(@RequestParam(value = "name", required = false)String name){
        fruitServer.getAccessToken();
        if(name==null){ // 이름 없이 요청했으면, 과일 이름 목록 전달
            return fruitServer.getFruitList();
        }else{
            return fruitServer.getFruit(name);
        }
    }

    @GetMapping("/vegetable")
    public Flux<?> getVegetableItems(@RequestParam(value = "name", required = false)String name){
        vegetableServer.getAccessToken();
        if(name==null){ // 이름 없이 요청했으면, 채소 이름 목록 전달
            return vegetableServer.getVegetableList();
        }else{
            return vegetableServer.getVegetable(name);
        }
    }


}

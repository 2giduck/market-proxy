package topia.duck.market.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import topia.duck.market.domain.Product;
import topia.duck.market.server.FruitServer;
import topia.duck.market.server.VegetableServer;

import java.time.Duration;

@RestController
@RequestMapping("/product")
public class MarketController {

    private final FruitServer fruitServer;
    private final VegetableServer vegetableServer;

    public MarketController(FruitServer fruitServer, VegetableServer vegetableServer) {
        this.fruitServer = fruitServer;
        this.vegetableServer = vegetableServer;
    }

    @GetMapping("/fruit")
    public Flux<ServerSentEvent<?>> getFruitItems(@RequestParam(value = "name", required = false)String name){
        fruitServer.getAccessToken();
        if(name==null){ // 이름 없이 요청했으면, 과일 이름 목록 전달
            return Flux.interval(Duration.ofSeconds(1))
                    .map(n->ServerSentEvent.builder(fruitServer.getFruitList()).build());
        }else{
            return Flux.interval(Duration.ofSeconds(1))
                    .map(n->ServerSentEvent.builder(fruitServer.getFruit(name)).build());
        }
    }


}

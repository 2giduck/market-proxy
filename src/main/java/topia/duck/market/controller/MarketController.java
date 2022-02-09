package topia.duck.market.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import topia.duck.market.server.FruitServer;
import topia.duck.market.server.VegetableServer;

@RestController
@RequestMapping("")
public class MarketController {

    private final FruitServer fruitServer;
    private final VegetableServer vegetableServer;

    public MarketController(FruitServer fruitServer, VegetableServer vegetableServer) {
        this.fruitServer = fruitServer;
        this.vegetableServer = vegetableServer;
    }
}

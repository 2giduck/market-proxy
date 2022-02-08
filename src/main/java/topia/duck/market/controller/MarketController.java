package topia.duck.market.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import topia.duck.market.server.FruitServerImpl;
import topia.duck.market.server.VegetableServerImpl;

@RestController
@RequestMapping("")
public class MarketController {

    private final FruitServerImpl fruitServerImpl;
    private final VegetableServerImpl vegetableServerImpl;

    public MarketController(FruitServerImpl fruitServerImpl, VegetableServerImpl vegetableServerImpl) {
        this.fruitServerImpl = fruitServerImpl;
        this.vegetableServerImpl = vegetableServerImpl;
    }
}

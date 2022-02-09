package topia.duck.market.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class Product {
    private String name;
    private int price;
}

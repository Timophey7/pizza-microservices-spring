package com.menuservice.menuservice.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductResponse {

    private String productName;
    private String descriptionOfProduct;
    private int price;
    private int productWeight;
    private String photoUrl;

}

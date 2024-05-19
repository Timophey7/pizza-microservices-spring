package com.customerservice.customerservice.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.config.CustomEditorConfigurer;

@Data
@Builder
public class ProductResponse {

    private String productName;
    private String descriptionOfProduct;
    private int price;
    private int productWeight;
    private String photoUrl;

}

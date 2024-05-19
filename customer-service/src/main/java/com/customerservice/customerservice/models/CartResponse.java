package com.customerservice.customerservice.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CartResponse implements Serializable {
    private int id;
    private String productName;
    private int productPrice;
    private String productUrl;
}

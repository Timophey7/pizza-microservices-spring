package com.menuservice.menuservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartResponse implements Serializable {
    private int id;
    private String productName;
    private int productPrice;
    private String productUrl;
}

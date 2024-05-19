package com.menuservice.menuservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "productName")
    private String productName;
    @Column(name = "descriptionOfProduct")
    private String descriptionOfProduct;
    @Column(name = "price")
    private int price;
    @Column(name = "productWeight")
    private int productWeight;
    @Column(name = "photoUrl")
    private String photoUrl;

}

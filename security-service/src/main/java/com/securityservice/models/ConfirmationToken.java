package com.securityservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "tokens")
public class ConfirmationToken {

    @Id
    @GeneratedValue
    private int id;
    private String userEmail;
    private String token;
    private Date expiryDate;

}

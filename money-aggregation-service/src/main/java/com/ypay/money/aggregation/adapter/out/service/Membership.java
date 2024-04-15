package com.ypay.money.aggregation.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership {


    private String membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;


}

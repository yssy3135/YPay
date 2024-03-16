package com.ypay.banking.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

// 이 Membership 클래스는 banking service만을 위한 클래스
// membership-service 의 membership과 매우 유사하지만 같지 않다.
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

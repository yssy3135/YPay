package com.ypay.membership.application.port.in;

import common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final boolean isValid;

    @NotNull
    private final boolean isCorp;


    @Builder
    public RegisterMembershipCommand(String name, String email, String address, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = true;
        this.isCorp = isCorp;

        this.validateSelf();
    }
}

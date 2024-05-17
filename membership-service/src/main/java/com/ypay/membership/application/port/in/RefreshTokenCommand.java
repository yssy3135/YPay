package com.ypay.membership.application.port.in;

import com.ypay.common.SelfValidating;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public
class RefreshTokenCommand extends SelfValidating<RefreshTokenCommand> {
    private final String refreshToken;

}

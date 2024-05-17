package com.ypay.membership.adapter.out.persistence;

import com.ypay.common.PersistenceAdapter;
import com.ypay.membership.adapter.out.valut.VaultAdapter;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.application.port.out.ModifyMembershipPort;
import com.ypay.membership.application.port.out.RegisterMembershipPort;
import com.ypay.membership.domain.JwtToken;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    private final VaultAdapter vaultAdapter;

    // adapter가 실제로 DB와 어떻게 연동될지 결정
    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        String encryptEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());

        return membershipRepository.save(
             new MembershipJpaEntity(
                    membershipName.getNameValue(),
                    membershipAddress.getAddressValue(),
                    encryptEmail,
                    membershipIsValid.isValidValue(),
                    membershipIsCorp.isCorpValue(),
                    null
            )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {


        MembershipJpaEntity membershipJpaEntity = membershipRepository.findById(Long.parseLong(membershipId.getMembershipId())).orElseThrow(() -> new RuntimeException("not found"));

        String encryptedEmailString = membershipJpaEntity.getEmail();
        String decryptedEmailString = vaultAdapter.decrypt(encryptedEmailString);
        membershipJpaEntity.setEmail(decryptedEmailString);
        return membershipJpaEntity;
    }


    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress address) {
        List<MembershipJpaEntity> membershipJpaEntityList = membershipRepository.findByAddress(address.getAddressValue());
        for (MembershipJpaEntity entity : membershipJpaEntityList) {
            String encryptedEmailString = entity.getEmail();
            String decryptedEmailString = vaultAdapter.decrypt(encryptedEmailString);
            entity.setEmail(decryptedEmailString);
        }

        return membershipJpaEntityList;
    }


    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {

        String encryptEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());

        MembershipJpaEntity entity = findMembership(membershipId);
        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(encryptEmail);
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());
        entity.setRefreshToken(membershipRefreshToken.getRefreshTokenValue());

        return membershipRepository.save(entity);
    }
}

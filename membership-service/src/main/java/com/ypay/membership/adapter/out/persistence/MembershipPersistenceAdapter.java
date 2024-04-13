package com.ypay.membership.adapter.out.persistence;

import com.ypay.common.PersistenceAdapter;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.application.port.out.ModifyMembershipPort;
import com.ypay.membership.application.port.out.RegisterMembershipPort;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    // adapter가 실제로 DB와 어떻게 연동될지 결정
    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        return membershipRepository.save(
             new MembershipJpaEntity(
                    membershipName.getNameValue(),
                    membershipAddress.getAddressValue(),
                    membershipEmail.getEmailValue(),
                    membershipIsValid.isValidValue(),
                    membershipIsCorp.isCorpValue()
            )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.findById(Long.parseLong(membershipId.getMembershipId())).orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        MembershipJpaEntity entity = findMembership(membershipId);
        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(membershipEmail.getEmailValue());
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());

        return membershipRepository.save(entity);
    }


    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress address) {

        return membershipRepository.findByAddress(address.getAddressValue());
    }
}

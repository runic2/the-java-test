package member;

import java.util.Optional;

import domain.Member;

public interface MemberService {

    Optional<Member> findById(Long memberId);
    
    void validate(Long memeberId);
}

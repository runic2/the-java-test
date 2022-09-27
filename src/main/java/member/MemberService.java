package member;

import java.util.Optional;

import domain.Member;
import domain.Study;

public interface MemberService {

    Optional<Member> findById(Long memberId);
    
    void validate(Long memeberId);
    
    void notify(Study newstudy);

    void notify(Member member);
}

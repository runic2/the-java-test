package study;

import java.util.Optional;

import domain.Member;
import domain.Study;
import member.MemberService;

public class StudyService {
	
	//의존성객체
	//DB접근 객체
    private final MemberService memberService;
    //JPA Table Interface
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        //null check
    	assert memberService != null;
        assert repository != null;
        
        this.memberService = memberService;
        this.repository = repository;
    }

    //study 객체를 만드는 method
    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'")));
        //생성
        return repository.save(study);//JPA method
    }

}

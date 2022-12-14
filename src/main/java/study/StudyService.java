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
        Study newstudy = repository.save(study);
        memberService.notify(newstudy);//새로운 study에 대한 알림
        memberService.notify(member.get());//member에 대한 알림
        return newstudy;//JPA method
    }
    
    public Study openStudy(Study study) {
        study.open();
        //localtime 저장, status -> OPENED
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }

}

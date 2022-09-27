package study;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Member;
import domain.Study;
import member.MemberService;

@ExtendWith(MockitoExtension.class)//Mock 생성 - 확장
class StudyServiceTest {
	
	@Mock
	MemberService memberService;
	
	@Mock
	StudyRepository studyRepository;
	
	@Test
    void createNewStudy(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        //Test할 객체 생성 
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");
        
        Study study = new Study(10, "java");
        
        //Stubbing
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        //any() => 모든 값 대응
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);
        
        //notify() 한번 실행
        studyService.createNewStudy(1L, study);
        
        //memberService의 notify(study) method가 1번 호출 되어야 한다. test
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        //validate()가 한번도 실행되지 않음.
        verify(memberService, never()).validate(any());
        
        //method 순서 확인
        InOrder inOrder = inOrder(memberService);        
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
        
        //어떠한 액션 이후에 더 이상 객체를 사용하지 않아야 함.
//        verifyNoInteractions(memberService);
        
    }
}
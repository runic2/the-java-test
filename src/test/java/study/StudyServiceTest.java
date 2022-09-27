package study;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
	@DisplayName("Mockito BDD 스타일 API")
    void createNewStudy(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
		// Given
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
//        when(memberService.findById(any())).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);
        
//        import static
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);
        
        // When
        //notify() 한번 실행
        studyService.createNewStudy(1L, study);
        
        // Then
        assertEquals(member, study.getOwner());
        //memberService의 notify(study) method가 1번 호출 되어야 한다. test
//        verify(memberService, times(1)).notify(study);
        then(memberService).should(times(1)).notify(study);
        //어떠한 액션 이후에 더 이상 객체를 사용하지 않아야 함.
//        verifyNoInteractions(memberService);
        then(memberService).shouldHaveNoInteractions();
        
    }
}
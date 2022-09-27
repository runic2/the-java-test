package study;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
    void createNewStudy(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        //Test할 객체 생성 
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");
        
        //Stubbing
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        //any() => 모든 값 대응
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        Study study = new Study(10, "java");
        //위 스터빙에 따라 1L => return member
        Optional<Member> findbyId = memberService.findById(1L);
        assertEquals("keesun@email.com", findbyId.get().getEmail());//true
        
        //특정값에 예외가 도출되게 함
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
        	memberService.validate(1L);
        });
        
        //여러번 호출 될 때,
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());
//        1.
        Optional<Member> byId = memberService.findById(1L);
        assertEquals("keesun@email.com", byId.get().getEmail());

//        2.
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });
        
//        3.
        assertEquals(Optional.empty(), memberService.findById(3L));
    }
}
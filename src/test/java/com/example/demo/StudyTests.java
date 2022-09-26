package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) //replace [underScore => blank]
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)//test 한 번만 Instance 함
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//test 순서를 정해줌
//@ExtendWith(FindSlowTestExtension.class)//선언적으로 등록 - 확장모델
class StudyTests {
	
	int value = 1;
	
//	확장 모델 - 프로그래밍 등록 @RegisterExtension
	@RegisterExtension
	static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);
	
	@Order(1)//test 순서를 정함. 첫번째
	@Test
//	@SlowTest
//	@Disabled -> unabled test unit
//	@Tag("slow")
	@DisplayName("스터디 만들기 tag slow")
	void create1_new_study_again () throws InterruptedException {
		Thread.sleep(1005L);
		System.out.println(value++);
		System.out.println("create slow tag test");
	}
	
	@Order(2)
	@FastTest //customAnnotation
	@DisplayName("스터디 만들기 ^^ tag fast")
//	@Tag("fast") //=> FastTest로 생략가능
	void create_new_study () {
		System.out.println(value++);
		System.out.println("create fast Tag test");
		
		
		
//		특정한 조건을 만족하는 경우에 테스트를 실행하는 방법.
//		환경변수 - test_env 가 local인 경우에 test 진행
//		String test_env = System.getenv("TEST_ENV");
//		System.out.println(test_env);
//		assumeTrue("LOCAL".equalsIgnoreCase(test_env));
		
//		특정한 조건을 건 테스트
//		assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
//			System.out.println("LOCAL");
//			Study actual = new Study(100);
//			assertThat(actual.getLimit()).isGreaterThan(0);
//			
//		});
		
		
//		특정 시간 안에 실행이 완료되는지 확인
//		assertTimeout(Duration.ofMillis(100), () -> {
//			new Study(10);
//			Thread.sleep(300);			
//		});
		// TODO ThreadLocal
//		assertTimeoutPreemptively(null, null); 로 작성이 @Transaction 형식의 rollback이 실행이 되지 않을 수도 있음.
//		300밀리초가 끝나지전에 종료
		
		
//		Test 객체의 예외 사항 받기
//		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
//		String message = exception.getMessage();
//		assertEquals("limit은 0보다 커야한다.", exception.getMessage());
		
		
		
//		Study study = new Study(-10);
		
//		모든 확인 구문 확인 executables
//		assertAll(
//			() -> assertNotNull(study),
//	//		값이 null이 아닌지 확인
//			() -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 "+ StudyStatus.DRAFT +" 상태한다."),
//	//		실제 값이 기대한 값과 같은지 확인
//	//		람다 문법 함수로 할 시, 오류가 생길 때만 실행함.		
//			() -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
//		);
		
		
		
//		assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
//			@Override
//			public String get() {
//				// TODO Auto-generated method stub
//				return "오류 상태";
//			}
//		});
	}
	
	@Order(4)
	//@DisplayName("스터디 만들기 반복 - disabled")
	@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition} / {totalRepetitions}")// 10번 반복, 반복명 설정
	@Disabled
	void create_study_repeat (RepetitionInfo repetitaionInfo) {//반복 상세 내용 받기
		System.out.println("test  " + repetitaionInfo.getCurrentRepetition() + "/" + repetitaionInfo.getTotalRepetitions());
	}
	
	@Order(3)
	@DisplayName("스터디 만들기 반복2")
	@ParameterizedTest(name = "{index} {displayName} message={0}")//밑 파라미터 수 만큼 반복
//	@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
//	@EmptySource
//	@NullSource
//	@NullAndEmptySource // null값과 빈 문자열을 반복에 추가
//	@ValueSource(ints = {10, 20, 30})//Study study => sysout - study.getLimit() , 자동 컨버터?
	@CsvSource({"10, '자바 스터디'", "20, '스프링'"}) // 인자 2개 이상
	void parameterizedTest (@AggregateWith(StudyAggregator.class) Study study) { 
		
		System.out.println(study);
		
//		Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
//		System.out.println(study);
//		인자 - ArgumentsAccessor argumentsAccessor
		
		//		System.out.println(new Study(limit, name)); // 인자 Integer limit , String name
		
		
//		@ValueSource(ints = {10, 20, 30})
//		System.out.println(study.getLimit());
//		인자 - @ConvertWith(StudyConverter.class) Study study
		
//		기본
//		@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
//		매개 변수 String message
//		sysout(message);
	}
	
	//반드시 inner static class이거나, public class 이여야 한다.
	static class StudyAggregator implements ArgumentsAggregator {
		@Override
		public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
				throws ArgumentsAggregationException {
			// TODO Auto-generated method stub
			Study study = new Study(accessor.getInteger(0), accessor.getString(1));
			return study;
		}
	}
	
	//인자가 하나인 경우만
	static class StudyConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			// TODO Auto-generated method stub
			assertEquals(Study.class, targetType, "Can only convert to study");
			return new Study(Integer.parseInt(source.toString()));
		}
	}

	@Test
	@DisplayName("스터디 만들기 조건 ^^")
	@EnabledIfEnvironmentVariable(named="TEST_ENV", matches = "LOCAL")//이 경우에만 실행
	void create_new_study_test_env () {
		
	}
	
//	@Test
//	@DisplayName("스터디 만들기 조건 ^^")
//	@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
//	void create_new_study_java () {
//		
//	}
	//@DisabledOnJre()
	
//	@Test
//	@DisplayName("스터디 만들기 조건 ^^")
//	@EnabledOnOs({OS.MAC, OS.LINUX})
//	void create_new_study_enableOnOS () {
//		
//	}
//	
//	@Test
//	@DisplayName("스터디 만들기 조건 ^^")
//	@DisabledOnOs(OS.WINDOWS)
//	void create_new_study_disableOnOS () {
//		
//	}
	


	@BeforeAll
	void beforeAll () {
		System.out.println("before all");
		//한번만 instance 하기 때문에 static일 필요 없음
	}
	
	@AfterAll
	void afterAll () {
		System.out.println("after all");
		//한번만 instance 하기 때문에 static일 필요 없음
	}
	
	@BeforeEach
	void beforeEach () {
		System.out.println("before each");
	}
	
	@AfterEach
	void afterEach () {
		System.out.println("after each");
	}
}

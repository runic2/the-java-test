package study;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {

}

package WebQuizEngine.repository;

import WebQuizEngine.model.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    Page<SolvedQuiz> findAllByUsername(String username, Pageable pageable);

}

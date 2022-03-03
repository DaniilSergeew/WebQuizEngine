package WebQuizEngine.repository;

import WebQuizEngine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findById(Long id);

    Page<Quiz> findAll(Pageable pageable);

    void delete(Quiz quiz);

}

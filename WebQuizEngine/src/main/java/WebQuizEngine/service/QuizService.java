package WebQuizEngine.service;

import WebQuizEngine.model.Quiz;
import WebQuizEngine.model.SolvedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface QuizService {
    void addNewQuiz(Quiz quiz, UserDetails details);

    Page<Quiz> getAll(int page);

    Optional<Quiz> getQuizById(Long id);

    void delete(Long id, UserDetails details);

    Page<SolvedQuiz> getCompletedQuiz(int page, UserDetails userDetails);

    void addSolvedQuiz(Quiz quiz, UserDetails userDetails);
}

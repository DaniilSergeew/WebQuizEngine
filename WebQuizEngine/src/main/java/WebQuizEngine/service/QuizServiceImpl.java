package WebQuizEngine.service;

import WebQuizEngine.model.Quiz;
import WebQuizEngine.model.SolvedQuiz;
import WebQuizEngine.repository.SolvedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class QuizServiceImpl implements QuizService {
    @Autowired
    WebQuizEngine.repository.QuizRepository QuizRepository;

    @Autowired
    SolvedQuizRepository solvedQuizRepository;

    @Override
    public void addNewQuiz(Quiz quiz, UserDetails details) {
        quiz.setAuthor(details.getUsername());
        QuizRepository.save(quiz);
    }

    @Override
    public Page<Quiz> getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return QuizRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Quiz> getQuizById(Long id) {
        return QuizRepository.findById(id);
    }

    @Override
    public void delete(Long id, UserDetails details) {
        Optional<Quiz> quiz = QuizRepository.findById(id);
        try {
            Quiz q = quiz.get();
            if (q.getAuthor().equals(details.getUsername())) {
                QuizRepository.delete(q);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Page<SolvedQuiz> getCompletedQuiz(int page, UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return solvedQuizRepository.findAllByUsername(userDetails.getUsername(), pageRequest);
    }

    public void addSolvedQuiz(Quiz quiz, UserDetails userDetails) {
        SolvedQuiz solvedQuiz = new SolvedQuiz();
        solvedQuiz.setCompletedAt(LocalDateTime.now());
        solvedQuiz.setQuestionId(quiz.getId());
        solvedQuiz.setUsername(userDetails.getUsername());
        solvedQuizRepository.save(solvedQuiz);
    }

}

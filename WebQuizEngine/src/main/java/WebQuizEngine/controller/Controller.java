package WebQuizEngine.controller;

import WebQuizEngine.model.Answer;
import WebQuizEngine.model.Quiz;
import WebQuizEngine.model.SolvedQuiz;
import WebQuizEngine.model.User;
import WebQuizEngine.service.QuizService;
import WebQuizEngine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;

    @GetMapping("/quizzes")
    public Page<Quiz> getQuizList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return quizService.getAll(page);
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> createNewQuiz(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Quiz quiz) {
        quizService.addNewQuiz(quiz, details);
        return new ResponseEntity<>(quizService.getQuizById(quiz.getId()), HttpStatus.OK);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        if (quiz.isPresent()) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(@PathVariable Long id, @RequestBody Answer answer,
                                       @AuthenticationPrincipal UserDetails details) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        try {
            Quiz q = quiz.get();
            if (q.getAnswer().equals(answer.getAnswer())) {
                quizService.addSolvedQuiz(q, details);
                return new ResponseEntity<>("{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        quizService.delete(id, details);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/quizzes/completed")
    public Page<SolvedQuiz> getCompletedQuiz(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        return quizService.getCompletedQuiz(page, userDetails);
    }
}

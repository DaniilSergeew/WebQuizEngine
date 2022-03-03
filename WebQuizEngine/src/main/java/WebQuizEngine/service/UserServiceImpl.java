package WebQuizEngine.service;

import WebQuizEngine.model.User;
import WebQuizEngine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void addUser(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

}

package WebQuizEngine.service;

import WebQuizEngine.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void addUser(User user);

}

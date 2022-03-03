package WebQuizEngine.service;

import WebQuizEngine.model.User;
import WebQuizEngine.repository.UserRepository;
import WebQuizEngine.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        try {
            User u = user.get();
            return new UserDetailsImpl(u);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
    }
}

package learn.personalfinance.domain;

import learn.personalfinance.data.UserRepository;
import learn.personalfinance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUser(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword()); // Consider hashing the password here
        existingUser.setBalance(user.getBalance());
        existingUser.setTransactions(user.getTransactions());
        existingUser.setRecurringTransactions(user.getRecurringTransactions());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
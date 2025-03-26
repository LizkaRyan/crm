package site.easy.to.build.crm.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.UserRepository;
import site.easy.to.build.crm.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void deleteAllUselessData(){
        userRepository.deleteAllUselessData();
    }

    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> saveAll(List<User> users){
        insertBatch(users);
        return userRepository.findAll();
    }

    public void insertBatch(List<User> users){
        String sql = "INSERT INTO users (email, username, status) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, users, 50, (ps, entity) -> {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getStatus());
        });
    }
}

package ru.kata.spring.boot_security.demo.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaolmp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaolmp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        entityManager.persist(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void change(User user) {
        User user1 = entityManager.find(User.class, user.getId());
        user1.setUsername(user.getUsername());
        if (user.isPasswordChanged()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user1.setPassword(encodedPassword);
        }

        user1.setRoles(user.getRoles());
        entityManager.merge(user1);
    }

    @Override
    public List<User> listUsers() {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :un";
        List<User> users = entityManager.createQuery(jpql, User.class)
                .setParameter("un", username)
                .getResultList();
        if (users.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }
}

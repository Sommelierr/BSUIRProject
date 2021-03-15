package by.sommelierr.repository;

import by.sommelierr.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
    boolean existsByUsername(String username);
}

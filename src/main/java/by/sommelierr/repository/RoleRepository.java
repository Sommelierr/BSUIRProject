package by.sommelierr.repository;


import by.sommelierr.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);
    Role findByName(String name);
    List<Role> findAll();

}

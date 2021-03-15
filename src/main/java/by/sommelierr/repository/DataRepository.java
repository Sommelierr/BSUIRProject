package by.sommelierr.repository;

import by.sommelierr.models.Data;
import by.sommelierr.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Long>
{
    List<Data> findAll();

}

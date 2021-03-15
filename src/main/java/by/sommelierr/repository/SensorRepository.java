package by.sommelierr.repository;

import by.sommelierr.models.Role;
import by.sommelierr.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findAll();
}

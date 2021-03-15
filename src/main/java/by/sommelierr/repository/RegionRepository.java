package by.sommelierr.repository;

import by.sommelierr.models.Region;
import by.sommelierr.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findAll();
    Region findById(int id);
}
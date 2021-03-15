package by.sommelierr.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {
    @Id
    private int id;
    private int number;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    List<Sensor> sensors;

    public Region() {
    }

    public int getId() {
        return id;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

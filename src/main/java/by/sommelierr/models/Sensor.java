package by.sommelierr.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    private int id;
    private int number;
    private String name;
    private String x;
    private String y;

    @OneToMany(fetch = FetchType.LAZY)
    List<Data> dataList;

    @ManyToOne(fetch = FetchType.LAZY)
    Region region;

    public Sensor() {
    }

    public int getId() { return id;}

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

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}

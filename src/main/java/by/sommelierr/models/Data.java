package by.sommelierr.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "data")
public class Data {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sensor sensor;
    private Date registrationData;
    private Date registrationTime;
    private int temperature;

    public Data() {
    }

    public int getId() {
        return id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Date getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(Date registrationData) {
        this.registrationData = registrationData;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", registrationData=" + registrationData +
                ", registrationTime=" + registrationTime +
                ", temperature=" + temperature +
                '}' + '\n';
    }
}

package com.myprojects.cardirectory.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name="cars")
public class Car {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "manufacturer")
    private String carManufacturer;
    
    @Column(name = "model")
    private String carModel;
    
    @Column(name = "color")
    private String carColor;

    @Column(name = "release_year")
    private int carReleaseYear;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time")
    private Calendar creationTime;
	
	public Car() {};
	
    public Car(String manufacturer, String model, String color, int releaseYear) {
        carManufacturer = manufacturer;
        carModel = model;
        carColor = color;
        carReleaseYear = releaseYear;
	}
    
    @Override
    public String toString() {
        return "manufacturer: " + carManufacturer + " model: " + carModel
               + " color: " + carColor + " release year: " + carReleaseYear
               + " creation date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creationTime.getTime());
    }

    public long getCarId() {
        return id;
    }

    public void setCarId(long carId) {
        this.id = carId;
	}

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(String carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public int getCarReleaseYear() {
        return carReleaseYear;
    }

    public void setCarReleaseYear(int carReleaseYear) {
        this.carReleaseYear = carReleaseYear;
    }
    
    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Calendar creationTime) {
        this.creationTime = creationTime;
    }
	
}

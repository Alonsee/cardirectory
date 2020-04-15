package com.myprojects.cardirectory.service;

import java.util.List;
import java.util.Map;

import com.myprojects.cardirectory.pojo.Car;

public interface CarService {
    
	public List<Car> findCars(Car car, String orderBy);
	
    public Car findCarById(Long id);

    public void addCar(Car car);
    
    public void deleteCar(long id);
    
    public Map<String, String> getStatistics();

}

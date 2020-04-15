package com.myprojects.cardirectory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myprojects.cardirectory.pojo.Car;
import com.myprojects.cardirectory.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService{

    private CarRepository carRepository;
    
    public CarServiceImpl() {};
    
    @Autowired
    public CarServiceImpl(CarRepository repository) {
    	carRepository = repository;
    }

    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deleteCar(long id) {
	    carRepository.deleteById(id);
	}

    @Override
    public List<Car> findCars(Car car, String orderBy) {
        String manufacturer = car.getCarManufacturer();
        String model = car.getCarModel();
        String color = car.getCarColor();
        int year = car.getCarReleaseYear();
        if ( year > 0) {
            return carRepository.findCarByParamsWithYear(manufacturer, model, color, year, Sort.by(orderBy));
        } 
        	return carRepository.findCarByParams(manufacturer, model, color, Sort.by(orderBy));
    }

	@Override
	public Car findCarById(Long id) {
		List<Car> car = carRepository.findCarById(id);
		return (car.size() == 0 ? null : car.get(0));
	}
	
	public Map<String, String> getStatistics(){
		Map<String, String> stats = new HashMap<String, String>();
		stats.put("Count of cars", String.valueOf(carRepository.count()));
		stats.put("First record", carRepository.getFirstRecord().get(0).toString());
		stats.put("Last record", carRepository.getLastRecord().get(0).toString());
		return stats;
	}

}

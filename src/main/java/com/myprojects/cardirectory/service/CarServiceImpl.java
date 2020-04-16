package com.myprojects.cardirectory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myprojects.cardirectory.pojo.Car;
import com.myprojects.cardirectory.repository.CarRepository;

/**
 * Класс для работы с объектами Car
 */
@Service
public class CarServiceImpl implements CarService{

    private CarRepository carRepository;
    
    public CarServiceImpl() {};
    
    @Autowired
    public CarServiceImpl(CarRepository repository) {
    	carRepository = repository;
    }

    /**
     * Метод для создания новой записи
     */
    @Override
    public void addCar(Car car) {
        carRepository.save(car);
    }

    /**
     * Метод для удаления записи по id
     */
    @Override
    public void deleteCar(long id) {
	    carRepository.deleteById(id);
	}

    /**
     * Метод для поиска записей по параметрам
     * с сортировкой
     */
    @Override
    public List<Car> findCars(Car car, String orderBy) {
        String manufacturer = car.getCarManufacturer();
        String model = car.getCarModel();
        String color = car.getCarColor();
        int year = car.getCarReleaseYear();
        // В зависимости от указания года в параметрах поиска используются разные методы carRepository
        if ( year > 0) {
            return carRepository.findCarByParamsWithYear(manufacturer, model, color, year, Sort.by(orderBy));
        } 
        	return carRepository.findCarByParams(manufacturer, model, color, Sort.by(orderBy));
    }

    /**
     * Метод для поиска записей по id
     */
	@Override
	public Car findCarById(Long id) {
		List<Car> car = carRepository.findCarById(id);
		return (car.size() == 0 ? null : car.get(0));
	}
	
	/**
	 * Метод для получения статистики из базы данных
	 */
	public Map<String, Object> getStatistics(){
		Map<String, Object> stats = new HashMap<String, Object>();
		stats.put("Count of cars", carRepository.count());
		stats.put("First record", carRepository.getFirstRecord().get(0));
		stats.put("Last record", carRepository.getLastRecord().get(0));
		stats.put("Oldest car", carRepository.getOldestCar().get(0));
		stats.put("Newest car", carRepository.getNewestCar().get(0));
		return stats;
	}

}

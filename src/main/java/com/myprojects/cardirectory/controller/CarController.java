package com.myprojects.cardirectory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.myprojects.cardirectory.pojo.Car;
import com.myprojects.cardirectory.service.CarService;

/**
 * Контроллер с маппингом /cars
 */
@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;
    
    public CarController() {};
    
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }
    
    /**
     * Метод принимает Get запрос с параметрами поиска и параметром сортировки
     * возвращает массив объектов car
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Car> getCars(@RequestParam(required = false, defaultValue = "_%", name = "manufacturer") String carManufacturer,
                             @RequestParam(required = false, defaultValue = "_%", name = "model") String carModel,
                             @RequestParam(required = false, defaultValue = "_%", name = "color") String carColor,
                             @RequestParam(required = false, defaultValue = "0", name = "year") int carReleaseYear,
                             @RequestParam(required = false, defaultValue = "carManufacturer", name = "orderby") String orderby) {
    	List<Car> cars = carService.findCars(new Car(carManufacturer, carModel, carColor, carReleaseYear), orderby);
        return cars;
    }
    
    /**
     * Метод принимает Post запрос для создания записи с объектом car. 
     * Если автомобиля с такими параметрами нет в базе
     * создает и возвращает сообщение об успешной операции,
     *  если есть возвращает сообщение с предупреждением
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createCar(@RequestBody Car car){
    	
    	car.setCreationTime(Calendar.getInstance());
    	
        if ((carService.findCars(car, "carManufacturer").size() == 0)
        	    && !(car.getCarManufacturer().equals(""))
        	    && !(car.getCarModel().equals(""))
            	&& !(car.getCarColor().equals(""))
        	    && !(car.getCarReleaseYear() == 0)) {
        	carService.addCar(car);
            return new ResponseEntity<Response>(new Response("OK"), HttpStatus.OK);
        } else if ((car.getCarManufacturer().equals(""))
            	   || (car.getCarModel().equals(""))
            	   || (car.getCarColor().equals(""))
            	   || (car.getCarReleaseYear() == 0)){
            return new ResponseEntity<Response>(new Response("Invalid parameters"), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response("Car is already exists"), HttpStatus.OK);
        }
    }
    
    /**
     * Метод принимает Post запрос на удаление с id записи.
     * Если запись с таким id существует, она удаляеться и 
     * возвращаеться сообщение об успешной операции,
     * если такой записи нет возвращается
     * сообщение с предупреждением
     */
    @PostMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> deleteCar(@PathVariable long id) {
    	
        if (carService.findCarById(id) != null) {
        	carService.deleteCar(id);
        	return new ResponseEntity<Response>(new Response("OK"), HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<Response>(new Response("Car not found"), HttpStatus.OK);
        }
    }
    
    /**
     * Метод возвращает массив со статистикой из базы данных
     */
    @GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getStatistics() {
    	
    	return carService.getStatistics();
    }
    
    /**
     * Класс служит оберткой для сообщений
     */
    private class Response {
    	private String message;
    	
    	public Response(String message) {
    		this.message = message;
    	}

		@SuppressWarnings("unused")
		public String getMessage() {
			return message;
		}
    } 
}


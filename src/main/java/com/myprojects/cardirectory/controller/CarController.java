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

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;
    
    public CarController() {};
    
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Car> getCars(@RequestParam(required = false, defaultValue = "_%", name = "manufacturer") String carManufacturer,
                             @RequestParam(required = false, defaultValue = "_%", name = "model") String carModel,
                             @RequestParam(required = false, defaultValue = "_%", name = "color") String carColor,
                             @RequestParam(required = false, defaultValue = "0", name = "year") int carReleaseYear,
                             @RequestParam(required = false, defaultValue = "carManufacturer", name = "orderby") String orderby) {
        System.out.println(carManufacturer+ carModel+carColor+ carReleaseYear);
    	List<Car> cars = carService.findCars(new Car(carManufacturer, carModel, carColor, carReleaseYear), orderby);
        return cars;
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createCar(@RequestBody Car car){
    	car.setCreationTime(Calendar.getInstance());
        if (carService.findCars(car, "carManufacturer").size() == 0) {
        	carService.addCar(car);
            return new ResponseEntity<Response>(new Response("OK"), HttpStatus.OK);
        } else {
           return new ResponseEntity<Response>(new Response("Car is already exists"), HttpStatus.OK);
        }
    }
    
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
    
    
    @GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getStatistics() {
    	return carService.getStatistics();
    }
    
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


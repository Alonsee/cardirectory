package com.myprojects.cardirectory.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myprojects.cardirectory.pojo.Car;

public interface CarRepository extends CrudRepository<Car,Long>{

    public List<Car> findCarById(long id);
    
    public List<Car> findCarByCarManufacturer(String manufacturer);
    
    public List<Car> findCarByCarModel(String model);
    
    public List<Car> findCarByCarReleaseYear(int year);
    
    @Query("select c from Car c where c.carManufacturer like :manufacturer"
                               + " and c.carModel like :model"
                               + " and c.carColor like :color")
    public List<Car> findCarByParams(@Param("manufacturer") String manufacturer, 
                                     @Param("model") String model,
                                     @Param("color") String color,
                                     Sort sort);
    
    @Query("select c from Car c where c.carManufacturer like :manufacturer"
                              + " and c.carModel like :model"
                              + " and c.carColor like :color"
                              + " and c.carReleaseYear = :year")
    public List<Car> findCarByParamsWithYear(@Param("manufacturer") String manufacturer, 
                                             @Param("model") String model,
                                             @Param("color") String color,
                                             @Param("year") int year,
                                             Sort sort);
    
    @Query("select count(*) from Car")
    public int getCarsCount();
    
    @Query("select c from Car c where c.creationTime = (select min(creationTime) from Car)")
    public List<Car> getFirstRecord();
    
    @Query("select c from Car c where c.creationTime = (select max(creationTime) from Car)")
    public List<Car> getLastRecord();

}

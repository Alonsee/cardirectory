package com.myprojects.cardirectory.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myprojects.cardirectory.pojo.Car;

/**
 * Репозиторий для работы с базой данных cars
 */
public interface CarRepository extends CrudRepository<Car,Long>{

    public List<Car> findCarById(long id);
    
    /**
     * Метод поиска автомобиля по параметрам
     * @param manufacturer
     * @param model
     * @param color
     * с сортировкой по параметру
     * @param sort
     */
    @Query("select c from Car c where c.carManufacturer like :manufacturer"
                               + " and c.carModel like :model"
                               + " and c.carColor like :color")
    public List<Car> findCarByParams(@Param("manufacturer") String manufacturer, 
                                     @Param("model") String model,
                                     @Param("color") String color,
                                     Sort sort);
    /**
     * Метод поиска автомобиля по параметрам
     * @param manufacturer
     * @param model
     * @param color
     * @param year
     * с сортировкой по параметру
     * @param sort
     */
    @Query("select c from Car c where c.carManufacturer like :manufacturer"
                              + " and c.carModel like :model"
                              + " and c.carColor like :color"
                              + " and c.carReleaseYear = :year")
    public List<Car> findCarByParamsWithYear(@Param("manufacturer") String manufacturer, 
                                             @Param("model") String model,
                                             @Param("color") String color,
                                             @Param("year") int year,
                                             Sort sort);
    
    /**
     * Метод возвращает количество записей в таблице
     */
    @Query("select count(*) from Car")
    public int getCarsCount();
    
    /**
     * Метод для поиска первой созданной записи
     */
    @Query("select c from Car c where c.creationTime = (select min(creationTime) from Car)")
    public List<Car> getFirstRecord();
    
    /**
     * Метод для поиска последней созданной записи
     */
    @Query("select c from Car c where c.creationTime = (select max(creationTime) from Car)")
    public List<Car> getLastRecord();
    
    /**
     * Метод для поиска самого старого автомобиля
     */
    @Query("select c from Car c where c.carReleaseYear = (select max(carReleaseYear) from Car)")
    public List<Car> getOldestCar();
    
    /**
     * Метод для поиска самого нового автомобиля
     */
    @Query("select c from Car c where c.carReleaseYear = (select max(carReleaseYear) from Car)")
    public List<Car> getNewestCar();

}

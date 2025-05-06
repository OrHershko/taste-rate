package com.tasterate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasterate.model.Dish;
import com.tasterate.repository.DishRepository;
import com.tasterate.repository.RestaurantRepository;

@Service
public class DishService {
    
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }
    
    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }
    
    public List<Dish> getDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }
    
    @Transactional
    public Optional<Dish> addDishToRestaurant(Long restaurantId, Dish dish) {
        return restaurantRepository.findById(restaurantId)
            .map(restaurant -> {
                dish.setRestaurant(restaurant);
                return dishRepository.save(dish);
            });
    }
    
    @Transactional
    public boolean updateDish(Long id, Dish dishDetails) {
        return dishRepository.findById(id)
            .map(dish -> {
                dish.setName(dishDetails.getName());
                dish.setDescription(dishDetails.getDescription());
                dish.setPrice(dishDetails.getPrice());
                dish.setImageUrl(dishDetails.getImageUrl());
                dishRepository.save(dish);
                return true;
            })
            .orElse(false);
    }
    
    @Transactional
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }
    
    public List<Dish> searchDishesByName(String name) {
        return dishRepository.findByNameContainingIgnoreCase(name);
    }
}
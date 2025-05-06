package com.tasterate.service;

import com.tasterate.model.Restaurant;
import com.tasterate.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }
    
    public List<Restaurant> searchRestaurantsByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Restaurant> searchRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineContainingIgnoreCase(cuisine);
    }
    
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
    
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
    
    @Transactional
    public boolean updateRestaurant(Long id, Restaurant restaurantDetails) {
        return restaurantRepository.findById(id)
            .map(restaurant -> {
                restaurant.setName(restaurantDetails.getName());
                restaurant.setAddress(restaurantDetails.getAddress());
                restaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());
                restaurant.setCuisine(restaurantDetails.getCuisine());
                restaurant.setDescription(restaurantDetails.getDescription());
                restaurant.setImageUrl(restaurantDetails.getImageUrl());
                restaurantRepository.save(restaurant);
                return true;
            })
            .orElse(false);
    }
}
package com.felixstanley.makanmoerahcalculateaveragestarjob.dao;

import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Felix
 */
public interface RestaurantReviewDao extends JpaRepository<RestaurantReview, Integer> {

  @Query("SELECT r.restaurantId FROM RestaurantReview r WHERE r.approved = true "
      + "GROUP BY r.restaurantId ORDER BY r.restaurantId ASC")
  List<Integer> findRestaurantIdApprovedReview();

}

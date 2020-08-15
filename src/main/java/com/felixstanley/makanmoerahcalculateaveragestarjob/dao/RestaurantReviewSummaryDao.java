package com.felixstanley.makanmoerahcalculateaveragestarjob.dao;

import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Felix
 */
public interface RestaurantReviewSummaryDao extends
    JpaRepository<RestaurantReviewSummary, Integer> {

}

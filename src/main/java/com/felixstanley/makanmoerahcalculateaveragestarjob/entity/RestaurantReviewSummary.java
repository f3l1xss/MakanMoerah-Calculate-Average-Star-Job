package com.felixstanley.makanmoerahcalculateaveragestarjob.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

/**
 * @author Felix
 */
@Entity
@Data
public class RestaurantReviewSummary implements Serializable {

  @Id
  @Column(name = "restaurant_id")
  private Integer restaurantId;

  @Column(name = "average_star")
  private Double averageStar;

}

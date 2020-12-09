package com.felixstanley.makanmoerahcalculateaveragestarjob.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Felix
 */
@Entity
@Table(name = "restaurant_review_summary")
@Data
public class RestaurantReviewSummary implements Serializable {

  @Id
  @Column(name = "restaurant_id")
  private Integer restaurantId;

  @Column(name = "average_star")
  private Double averageStar;

}

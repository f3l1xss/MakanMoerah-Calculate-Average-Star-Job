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
@Table(name = "restaurant_review")
@Data
public class RestaurantReview implements Serializable {

  @Id
  @Column(name = "booking_id")
  private Integer bookingId;

  @Column(name = "restaurant_id")
  private Integer restaurantId;

  private Short star;

  private Boolean approved;

}

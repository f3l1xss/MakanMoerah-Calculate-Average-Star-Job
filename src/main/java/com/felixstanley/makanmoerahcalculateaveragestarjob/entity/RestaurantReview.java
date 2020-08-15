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
public class RestaurantReview implements Serializable {

  @Id
  @Column(name = "booking_id")
  private Integer bookingId;

  @Column(name = "restaurant_id")
  private Integer restaurantId;

  private Short star;

  private Boolean approved;

}

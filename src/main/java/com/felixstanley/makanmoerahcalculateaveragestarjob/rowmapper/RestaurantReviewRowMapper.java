package com.felixstanley.makanmoerahcalculateaveragestarjob.rowmapper;

import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReview;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Felix
 */
public class RestaurantReviewRowMapper implements RowMapper<RestaurantReview> {

  // Restaurant Review Column Names
  private static final String BOOKING_ID_COLUMN_NAME = "booking_id";
  private static final String RESTAURANT_ID_COLUMN_NAME = "restaurant_id";
  private static final String STAR_COLUMN_NAME = "star";

  @Override
  public RestaurantReview mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    RestaurantReview restaurantReview = new RestaurantReview();
    restaurantReview.setBookingId(resultSet.getInt(BOOKING_ID_COLUMN_NAME));
    restaurantReview.setRestaurantId(resultSet.getInt(RESTAURANT_ID_COLUMN_NAME));
    restaurantReview.setStar(resultSet.getShort(STAR_COLUMN_NAME));
    return restaurantReview;
  }
}

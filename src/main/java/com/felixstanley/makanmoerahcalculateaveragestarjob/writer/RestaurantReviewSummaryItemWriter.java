package com.felixstanley.makanmoerahcalculateaveragestarjob.writer;

import com.felixstanley.makanmoerahcalculateaveragestarjob.dao.RestaurantReviewSummaryDao;
import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReview;
import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReviewSummary;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

/**
 * @author Felix
 */
@AllArgsConstructor
@Slf4j
public class RestaurantReviewSummaryItemWriter implements ItemWriter<RestaurantReview> {

  private RestaurantReviewSummaryDao restaurantReviewSummaryDao;

  private Integer restaurantId;

  @Override
  public void write(List<? extends RestaurantReview> items) throws Exception {
    log.info("Calculating Average Star for Restaurant with ID: {} from {} approved reviews",
        restaurantId, items.size());
    Double averageStar = items.stream().collect(Collectors.averagingInt(RestaurantReview::getStar));
    log.info("Calculated Average Star of {}", averageStar);

    // Update Restaurant Review Summary with the resulted Average Star
    RestaurantReviewSummary existingRestaurantReviewSummary = restaurantReviewSummaryDao
        .findById(restaurantId).orElse(null);
    if (existingRestaurantReviewSummary != null) {
      // Update Existing Restaurant Review Summary
      log.info("Updating Average Star for Restaurant with ID: {}, from {} To {}", restaurantId,
          existingRestaurantReviewSummary.getAverageStar(), averageStar);
      existingRestaurantReviewSummary.setAverageStar(averageStar);
      restaurantReviewSummaryDao.saveAndFlush(existingRestaurantReviewSummary);
    } else {
      createNewRestaurantReviewSummary(averageStar);
    }
  }

  private void createNewRestaurantReviewSummary(Double averageStar) {
    RestaurantReviewSummary restaurantReviewSummary = new RestaurantReviewSummary();
    restaurantReviewSummary.setRestaurantId(restaurantId);
    restaurantReviewSummary.setAverageStar(averageStar);
    restaurantReviewSummaryDao.saveAndFlush(restaurantReviewSummary);
  }

}

package com.felixstanley.makanmoerahcalculateaveragestarjob.partitioner;

import com.felixstanley.makanmoerahcalculateaveragestarjob.constant.Constants;
import com.felixstanley.makanmoerahcalculateaveragestarjob.dao.RestaurantReviewDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

/**
 * @author Felix
 */
@AllArgsConstructor
@Slf4j
public class RestaurantIdPartitioner implements Partitioner {

  private static final String PARTITION_KEY = "partition";

  private RestaurantReviewDao restaurantReviewDao;

  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {
    log.info("Obtaining RestaurantId from Approved Restaurant Review...");
    List<Integer> restaurantIds = restaurantReviewDao.findRestaurantIdApprovedReview();
    log.info("Obtained following Restaurant Ids: {}", restaurantIds);
    Map<String, ExecutionContext> map = new HashMap<>(gridSize);
    int i = 0;
    for (Integer id : restaurantIds) {
      map.put(PARTITION_KEY + i, getExecutionContext(id));
      i++;
    }
    return map;
  }

  private ExecutionContext getExecutionContext(Integer restaurantId) {
    ExecutionContext executionContext = new ExecutionContext();
    executionContext.put(Constants.RESTAURANT_ID_EXECUTION_CONTEXT_KEY_NAME, restaurantId);
    return executionContext;
  }
}

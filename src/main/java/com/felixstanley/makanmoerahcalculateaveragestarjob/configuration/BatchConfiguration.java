package com.felixstanley.makanmoerahcalculateaveragestarjob.configuration;

import com.felixstanley.makanmoerahcalculateaveragestarjob.constant.Constants;
import com.felixstanley.makanmoerahcalculateaveragestarjob.dao.RestaurantReviewDao;
import com.felixstanley.makanmoerahcalculateaveragestarjob.dao.RestaurantReviewSummaryDao;
import com.felixstanley.makanmoerahcalculateaveragestarjob.entity.RestaurantReview;
import com.felixstanley.makanmoerahcalculateaveragestarjob.listener.LoggingJobListener;
import com.felixstanley.makanmoerahcalculateaveragestarjob.partitioner.RestaurantIdPartitioner;
import com.felixstanley.makanmoerahcalculateaveragestarjob.rowmapper.RestaurantReviewRowMapper;
import com.felixstanley.makanmoerahcalculateaveragestarjob.writer.RestaurantReviewSummaryItemWriter;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Felix
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private RestaurantReviewDao restaurantReviewDao;

  @Autowired
  private RestaurantReviewSummaryDao restaurantReviewSummaryDao;

  @Bean(name = "calculateAverageStarJob")
  public Job calculateAverageStarJob(Step calculateAverageStarJobManager) {
    return this.jobBuilderFactory.get("calculateAverageStarJob").listener(loggingJobListener())
        .start(calculateAverageStarJobManager).build();
  }

  @Bean
  public Step calculateAverageStarJobManager(Partitioner restaurantIdPartitioner,
      Step calculateAverageStar) {
    return this.stepBuilderFactory.get("calculateAverageStar.manager")
        .partitioner(calculateAverageStar.getName(), restaurantIdPartitioner)
        .step(calculateAverageStar).allowStartIfComplete(true).build();
  }

  @Bean
  public Partitioner restaurantIdPartitioner() {
    return new RestaurantIdPartitioner(restaurantReviewDao);
  }

  @Bean
  public Step calculateAverageStar(ItemReader restaurantReviewItemReader,
      ItemWriter restaurantReviewSummaryItemWriter) {
    return ((SimpleStepBuilder) this.stepBuilderFactory.get("calculateAverageStar")
        .chunk(Integer.MAX_VALUE)
        .reader(restaurantReviewItemReader)
        .writer(restaurantReviewSummaryItemWriter).allowStartIfComplete(true)).build();
  }

  public RowMapper<RestaurantReview> restaurantReviewRowMapper() {
    return new RestaurantReviewRowMapper();
  }

  @Bean
  @StepScope
  public JdbcCursorItemReader<RestaurantReview> restaurantReviewItemReader(@Value(
      "#{stepExecutionContext[" + Constants.RESTAURANT_ID_EXECUTION_CONTEXT_KEY_NAME
          + "]}") Integer restaurantId) {
    return new JdbcCursorItemReaderBuilder<RestaurantReview>().name("restaurantReviewItemReader")
        .dataSource(dataSource).rowMapper(restaurantReviewRowMapper()).sql("SELECT booking_id, "
            + "restaurant_id, star FROM restaurant_review WHERE approved=true "
            + "AND restaurant_id = ?").queryArguments(restaurantId).driverSupportsAbsolute(true)
        .build();
  }

  @Bean
  @StepScope
  public RestaurantReviewSummaryItemWriter restaurantReviewSummaryItemWriter(@Value(
      "#{stepExecutionContext[" + Constants.RESTAURANT_ID_EXECUTION_CONTEXT_KEY_NAME
          + "]}") Integer restaurantId) {
    return new RestaurantReviewSummaryItemWriter(restaurantReviewSummaryDao, restaurantId);
  }

  public JobExecutionListener loggingJobListener() {
    return new LoggingJobListener();
  }

}

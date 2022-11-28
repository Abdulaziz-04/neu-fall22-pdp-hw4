package portfolio.models.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.impl.DollarCostAverageSchedule;

public class DollarCostAverageScheduleTest {

  private final double EPSILON = 0.000000001;

  @Test
  public void createObj(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
    2000,
        30,
        LocalDate.parse("2020-10-10"),
        LocalDate.parse("2022-10-10"),
        5,
        null,
        buyingList
    ) ;

    assertEquals("dollar_cost_average", schedule.getName());
    assertEquals(30, schedule.getFrequencyDays());
    assertEquals(LocalDate.parse("2020-10-10"), schedule.getStartDate());
    assertEquals(LocalDate.parse("2022-10-10"), schedule.getEndDate());
    assertEquals(2000.0, schedule.getAmount(), EPSILON);
    assertEquals(5.0, schedule.getTransactionFee(), EPSILON);
    assertEquals(2, schedule.getBuyingList().size());
    assertEquals("AAPL", schedule.getBuyingList().get(0).getSymbol());
    assertEquals("AAA", schedule.getBuyingList().get(1).getSymbol());
    assertEquals(50.0, schedule.getBuyingList().get(0).getAmount(), EPSILON);
    assertEquals(50.0, schedule.getBuyingList().get(0).getAmount(), EPSILON);
  }

  @Test
  public void createObj_noEndDate(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        2000,
        30,
        LocalDate.parse("2020-10-10"),
        null,
        5,
        null,
        buyingList
    ) ;

    assertNull(schedule.getEndDate());
  }

  @Test
  public void createObj_endDateBeforeStartDate(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    try {
      BuySchedule schedule = new DollarCostAverageSchedule(
          2000,
          30,
          LocalDate.parse("2020-10-10"),
          LocalDate.parse("2020-01-01"),
          5,
          null,
          buyingList
      ) ;
      fail();
    }
    catch (Exception e) {
      assertEquals("endDate cannot be before startDate", e.getMessage());
    }
  }

  @Test
  public void createObj_frequencyLessThanZero(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    try {
      BuySchedule schedule = new DollarCostAverageSchedule(
          2000,
          -1,
          LocalDate.parse("2020-10-10"),
          LocalDate.parse("2022-01-01"),
          5,
          null,
          buyingList
      ) ;
      fail();
    }
    catch (Exception e) {
      assertEquals("Frequency day cannot be less than zero.", e.getMessage());
    }
  }

  @Test
  public void createObj_TransactionLessThanZero(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    try {
      BuySchedule schedule = new DollarCostAverageSchedule(
          2000,
          30,
          LocalDate.parse("2020-10-10"),
          LocalDate.parse("2022-01-01"),
          -1,
          null,
          buyingList
      ) ;
      fail();
    }
    catch (Exception e) {
      assertEquals("Commission fee cannot be negative.", e.getMessage());
    }
  }

  @Test
  public void createObj_buyingListEmpty(){
    List<Transaction> buyingList = new ArrayList<>();
    try {
      BuySchedule schedule = new DollarCostAverageSchedule(
          2000,
          30,
          LocalDate.parse("2020-10-10"),
          LocalDate.parse("2022-01-01"),
          5,
          null,
          buyingList
      ) ;
      fail();
    }
    catch (Exception e) {
      assertEquals("Stock buying list is empty", e.getMessage());
    }
  }

  @Test
  public void createObj_amountLessThanZero(){
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    try {
      BuySchedule schedule = new DollarCostAverageSchedule(
          -1,
          30,
          LocalDate.parse("2020-10-10"),
          LocalDate.parse("2022-01-01"),
          5,
          null,
          buyingList
      ) ;
      fail();
    }
    catch (Exception e) {
      assertEquals("Amount cannot be less than zero", e.getMessage());
    }
  }
}

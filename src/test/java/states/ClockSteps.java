package states;

import static org.junit.jupiter.api.Assertions.*;
import states.stopwatch.*;
import states.timer.*;

import io.cucumber.java.en.*;
import io.cucumber.java.Before;

class ClockSteps {
  private Context context;

  @Before
  void setUp() {
    // reset the initial values of timer to avoid inferences between different
    // consecutive tests
    context = new Context();
    context.currentState = IdleTimer.Instance(); // because we are testing the IdleTimer state here...
    AbstractTimer.resetInitialValues();
    AbstractStopwatch.resetInitialValues();
  }

  @Given("the Clock is in the {string} state")
  public void initial_state(String stateName) {
    context = new Context();
    AbstractTimer.resetInitialValues();
    AbstractStopwatch.resetInitialValues();
    // Verify starting state matches "Idle Timer"
    assertEquals(IdleTimer.Instance(), context.currentState);
  }

  @Given("the timer memory is {int}")
  public void timer_memory_is(Integer value) {
    assertEquals(value, AbstractTimer.getMemTimer());
  }

  @When("I press {string} and the clock {string}")
  public void i_press_and_the_clock(String button, String action) {
    switch (button.toLowerCase()) {
      case "right" -> context.right();
      case "up" -> context.up();
      case "left" -> context.left();
    }
    context.tick();
  }

  @When("the clock {string} again")
  public void the_clock_ticks_again(String action) {
    context.tick();
  }

  @Then("the state should be {string}")
  public void the_state_should_be(String expectedState) {
    switch (expectedState) {
      case "Set Timer" -> assertSame(SetTimer.Instance(), context.currentState);
      case "Paused Timer" -> assertSame(PausedTimer.Instance(), context.currentState);
      case "Reset Stopwatch" -> assertSame(ResetStopwatch.Instance(), context.currentState);
      case "Running Stopwatch" -> assertSame(RunningStopwatch.Instance(), context.currentState);
      case "Laptime Stopwatch" -> assertSame(LaptimeStopwatch.Instance(), context.currentState);
      case "Ringing Timer" -> assertSame(RingingTimer.Instance(), context.currentState);
      case "Idle Timer" -> assertSame(IdleTimer.Instance(), context.currentState);
    }
  }

  @Then("the memory timer should be {int}")
  @And("the memory timer should remain {int}")
  public void check_mem_timer(Integer expected) {
    assertEquals(expected, AbstractTimer.getMemTimer());
  }

  @Then("the timer value should be {int}")
  @And("the timer value should remain {int}")
  @And("the timer value should still be {int}")
  public void check_timer_value(Integer expected) {
    assertEquals(expected, AbstractTimer.getTimer());
  }

  @Then("the stopwatch total time and lap time should be {int}")
  public void check_stopwatch_reset(Integer expected) {
    assertEquals(expected, AbstractStopwatch.getTotalTime());
    assertEquals(expected, AbstractStopwatch.getLapTime());
  }

  @Then("the total time should be {int}")
  public void check_total_time(Integer expected) {
    assertEquals(expected, AbstractStopwatch.getTotalTime());
  }

  @And("the total time should be {int} and lap time should be {int}")
  public void check_total_and_lap(Integer total, Integer lap) {
    assertEquals(total, AbstractStopwatch.getTotalTime());
    assertEquals(lap, AbstractStopwatch.getLapTime());
  }

  @Then("the state should return to {string}")
  public void check_history_return(String state) {
    assertSame(PausedTimer.Instance(), context.currentState);
  }
}

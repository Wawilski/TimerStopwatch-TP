package states;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import states.timer.*;
import states.stopwatch.AbstractStopwatch;

class TimerTests {

  private static Context context;
  private ClockState current, newState;

  @BeforeEach
  void setup() {
    context = new Context(); // create the state machine context
    AbstractTimer.resetInitialValues();
  }

  @Test
  @DisplayName("Verify that the timer resets using left() in SetTimer")
  void testSetTimerLeft() {
    context.tick();
    context.right();

    context.tick();
    assertEquals(SetTimer.Instance(), context.currentState);
    context.left();
    assertEquals(0, AbstractTimer.getMemTimer());

  }

  @Test
  @DisplayName("Verify that the timer is increased by 5 using up() in SetTimer")
  void testSetTimerUp() {
    context.tick();
    context.right();

    context.tick();
    assertEquals(SetTimer.Instance(), context.currentState);
    context.up();
    assertEquals(6, AbstractTimer.getMemTimer());

  }

  @Test
  @DisplayName("Verify that the Timer starts in the Idle state with zeroed values")
  void testInitialState() {
    /*
     * When initialising the context (see setup() method above)
     * its currentState will be initialised with the initial state
     * of timer, i.e. the IdleTimer state.
     */
    current = context.currentState;

    assertEquals(Mode.timer, current.getMode());
    assertSame(IdleTimer.Instance(), current);
    assertEquals(0, AbstractTimer.getTimer(), "For the value of timer we ");
    assertEquals(0, AbstractTimer.getMemTimer(), "For the value of memTimer we ");
  }

  @Test
  @DisplayName("Ensure the default sub-state of AbstractTimer is IdleTimer")
  void testInitialAbstractTimer() {
    // The initial state of composite state AbstractTimer should be IdleTimer
    assertSame(AbstractTimer.Instance(), IdleTimer.Instance());
  }

  @Test
  @DisplayName("Ensure the default sub-state of ActiveTimer is RunningTimer")
  void testInitialActiveTimer() {
    // The initial state of composite state ActiveTimer should be RunningTimer
    assertSame(ActiveTimer.Instance(), RunningTimer.Instance());
  }

  @Test
  @DisplayName("Verify that the state machine correctly restores the Timer state using History")
  void testHistoryState() {
    current = AbstractTimer.Instance();
    // after processing the left() event, we should arrive in the initial state of
    // AbstractStopwatch
    newState = current.left();
    assertEquals(AbstractStopwatch.Instance(), newState);
    /*
     * after another occurrence of the left() event, we should return to the
     * original state
     * because we used history states
     */
    assertEquals(current, newState.left());
  }

}

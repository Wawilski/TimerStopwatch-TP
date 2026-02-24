Feature: Timer and Stopwatch Integration

Scenario: Full Lifecycle: Set timer, pause, switch to stopwatch, and return to ring
Given the device is in the "Idle Timer" state
And the timer memory is 0

When I press "right" and the clock "ticks"
Then the state should be "Set Timer"
And the memory timer should be 1

When the clock "ticks" again
Then the memory timer should be 2

When I press "right" and the clock "ticks"
Then the memory timer should remain 2

When I press "up" to start the timer
And the clock "ticks"
Then the timer value should be 1

When I press "up" to pause the timer
And the clock "ticks"
Then the state should be "Paused Timer"
And the timer value should remain 1

When I press "left" to switch mode
And the clock "ticks"
Then the state should be "Reset Stopwatch"
And the stopwatch total time and lap time should be 0

When I press "up" to start the stopwatch
And the clock "ticks"
Then the state should be "Running Stopwatch"
And the total time should be 1

When I press "up" to record a lap
And the clock "ticks"
Then the state should be "Laptime Stopwatch"
And the total time should be 2 and lap time should be 1

When I press "left" to return to timer mode
And the clock "ticks"
Then the state should return to "Paused Timer"
And the timer value should still be 1

When I press "up" to resume the timer
And the clock "ticks"
Then the state should be "Ringing Timer"
And the timer value should be 0

When I press "right" to stop the alarm
And the clock "ticks"
Then the state should be "Idle Timer"

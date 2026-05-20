# Segment LED Signal-Strength Visualizer

## Problem Description

A home lab enthusiast is building a network signal-strength monitor that displays the current Wi-Fi RSSI level on a Govee H6056 LED bar mounted vertically on the wall. The H6056 has 6 addressable segments. The physical bar is mounted with the cable connector at the top, which means the device's segment numbering runs from index 0 at the top down to index 5 at the bottom.

The user wants the bar to behave like a classic signal-strength indicator or thermometer: when signal is weak, only the bottom portion lights up; as signal improves, more of the bar fills upward. The gradient should intuitively indicate severity — red at the bottom for low signal, transitioning through yellow in the middle, and green at the top for strong signal.

A previous developer implemented a version using `segments.forEachIndexed { i, _ -> if (i < lit) setColor(i, GREEN) }` but users complained the bar looked wrong — it seemed to drain from the top instead of fill from the bottom, and the color was uniform. Additionally, when segments were "off", they sometimes retained their previous color instead of actually turning off.

The system runs as a long-lived background process. When the process exits (Ctrl+C or `kill`), the LED bar must be fully cleared rather than left displaying stale data.

## Output Specification

Implement the signal-strength visualizer in Kotlin. Produce the following files:

- `src/main/kotlin/SignalVisualizer.kt` — complete implementation with:
  - A function that takes a signal level (Int, 0–6) and renders it to the H6056
  - Correct segment index math for this device's physical orientation
  - The 3-zone color gradient applied correctly per zone
  - Proper handling of the "off" color for Govee hardware
  - A JVM shutdown hook that clears all segments when the process exits
  - A simulated main loop that cycles the signal level 0→6→0 so behavior can be observed
- `build.gradle.kts` — the Gradle build file with required dependencies

You may stub out the actual Govee HTTP API call (e.g., a `suspend fun setSegmentColor(index: Int, color: Triple<Int,Int,Int>)` that prints to stdout) so the logic can be verified without a physical device.

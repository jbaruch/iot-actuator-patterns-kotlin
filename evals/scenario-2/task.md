# Audio Volume Meter — Controller Never Updates

## Problem Description

A developer built a Kotlin application that drives a 6-segment Govee LED volume meter based on live microphone input. The audio pipeline reads RMS amplitude from a microphone at ~30 fps and feeds it into a controller that should update the LED bar's brightness level in real time.

The application has been running in the lab for a week, but the LED bar barely ever changes — it occasionally flickers to a new level but mostly stays stuck. The developer added a `println("Applied: $v")` and confirmed it almost never prints, even though the audio is clearly changing. They've ruled out the audio capture and the HTTP transport; the problem is somewhere in how values are submitted to the controller or how the controller decides to apply them.

The starter code is in the `starter/` directory. Review the implementation, identify all the bugs and design issues that explain why `onApply` is almost never called, and produce a corrected version.

## Output Specification

Produce the following files in your working directory:

- `fixed/AudioMeter.kt` — the corrected implementation with all issues resolved
- `fixed/build.gradle.kts` — updated build file with any missing dependencies added
- `diagnosis.md` — a short document (bullet points are fine) listing each problem found in the original code and what you changed to fix it

The fixed version should:
- Correctly drive a 6-segment Govee cloud-connected LED bar
- Use a value resolution appropriate for the device (a 6-segment bar can show ~7 distinct states)
- Include proper structured logging so the apply/throttle/submit events are observable
- Include diagnostic instrumentation that verifies how many distinct values are actually being submitted

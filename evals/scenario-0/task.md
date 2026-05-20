# Occupancy-Based Cloud Bulb Dimmer

## Problem Description

A co-working space operator wants to automate ambient lighting based on how many people are currently in the room. A camera system already runs a people-counting model at 30 frames per second, producing a normalized occupancy confidence score between 0.0 and 1.0 for each frame. The lighting rig consists of two independent Govee cloud-connected LED bulbs: one in the main seating area ("alpha") and one near the entrance ("beta").

The operator has tried a naive approach — calling the Govee cloud API directly inside the frame-processing loop — but the system quickly triggers HTTP 429 rate-limit errors and the camera loop stutters badly. A previous attempt used a simple `delay()` between API calls, but this blocked the entire processing pipeline.

The requirements are:
- Each bulb must be controlled independently with its own dedicated controller
- The occupancy score (0.0–1.0 float) must map to a small number of distinct brightness levels appropriate for the bulb's capabilities
- New frames should never block waiting for network I/O
- The system must not hammer the cloud API faster than it can handle sustained traffic
- There should be enough visibility into the system's behavior to diagnose issues without watching the process live

## Output Specification

Implement the occupancy-based dimmer in Kotlin. Produce the following files:

- `src/main/kotlin/OccupancyDimmer.kt` — the complete implementation including the controller setup and main entry point that wires the camera feed (simulated as a `Flow<Float>`) to both bulbs
- `build.gradle.kts` — the Gradle build file with all required dependencies declared

The main function should simulate a 10-second camera feed emitting random float occupancy values and drive both controllers, then cleanly shut down.

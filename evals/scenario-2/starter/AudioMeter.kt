import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Simulates raw audio RMS values from a microphone (0.0 to 1.0, very noisy)
fun audioFlow(): Flow<Float> = flow {
    var t = 0
    while (true) {
        // Simulate noisy mic: base level plus high-frequency noise
        val base = 0.5f + 0.3f * kotlin.math.sin(t * 0.1f)
        val noise = (Math.random() * 0.05 - 0.025).toFloat()
        emit(base + noise)
        delay(33) // ~30fps
        t++
    }
}

// Drives a 6-segment Govee LED volume meter
class VolumeController(
    private val onApply: suspend (Float) -> Unit
) {
    @Volatile private var pending: Float? = null
    @Volatile private var committed: Float? = null
    private var stable = 0
    private var lastApply = 0L
    private val minIntervalMs = 200L   // TODO: is this right for Govee cloud?

    fun submit(v: Float) { pending = v }

    fun start(scope: CoroutineScope) = scope.launch(Dispatchers.Default) {
        while (isActive) {
            delay(400)
            val v = pending ?: continue
            if (v == committed) { stable = 0; continue }
            stable++
            if (stable < 2) continue
            val now = System.currentTimeMillis()
            if (now - lastApply < minIntervalMs) continue
            try {
                onApply(v)
                committed = v
                stable = 0
                lastApply = now
                println("Applied: $v")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}

suspend fun applyVolumeLevel(level: Float) {
    // Stub: in production this calls the Govee cloud API
    println("Govee API: setting brightness to ${(level * 100).toInt()}%")
}

fun main() = runBlocking {
    val controller = VolumeController { level -> applyVolumeLevel(level) }
    controller.start(this)

    audioFlow().take(300).collect { rms ->
        controller.submit(rms)  // passes raw Float directly
    }

    delay(1000)
    println("Done")
}

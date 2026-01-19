package observer.diagnostics;

import observer.core.RuntimeSnapshot;
import java.util.ArrayDeque;
import java.util.Deque;

public final class DiagnosticsEngine {

    private static final int WINDOW = 10;
    private static final long GC_THRESHOLD = 200;
    private static final double CPU_THRESHOLD = 0.85;
    private static final double BLOCKED_RATIO = 0.2;

    private final Deque<RuntimeSnapshot> window = new ArrayDeque<>(WINDOW);

    public DiagnosticSignal analyze(RuntimeSnapshot s) {
        if (window.size() == WINDOW) window.removeFirst();
        window.addLast(s);

        if (s.gcPauseMillis > GC_THRESHOLD)
            return new DiagnosticSignal(DiagnosticSignal.Type.GC_PAUSE_ANOMALY,
                s.timestampMillis, "GC pause " + s.gcPauseMillis + "ms");

        if (s.liveThreads > 0 &&
            ((double)s.blockedThreads / s.liveThreads) > BLOCKED_RATIO)
            return new DiagnosticSignal(DiagnosticSignal.Type.THREAD_CONTENTION,
                s.timestampMillis, "High blocked thread ratio");

        if (s.processCpuLoad > CPU_THRESHOLD)
            return new DiagnosticSignal(DiagnosticSignal.Type.CPU_PRESSURE,
                s.timestampMillis, "High CPU pressure");

        return null;
    }
}
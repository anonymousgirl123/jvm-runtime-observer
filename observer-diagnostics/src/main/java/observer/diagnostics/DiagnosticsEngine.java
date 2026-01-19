package observer.diagnostics;

import observer.api.RuntimeSnapshot;
import observer.api.DiagnosticSignal;

public final class DiagnosticsEngine {

    public DiagnosticSignal analyze(RuntimeSnapshot s) {
        if (s.gcPauseMillis > 200) {
            return new DiagnosticSignal(
                    DiagnosticSignal.Type.GC_PAUSE_ANOMALY,
                    s.timestampMillis,
                    "GC pause " + s.gcPauseMillis + "ms"
            );
        }
        return null;
    }
}
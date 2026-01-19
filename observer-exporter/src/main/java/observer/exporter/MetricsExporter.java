package observer.exporter;

import observer.core.RuntimeSnapshot;
import observer.diagnostics.DiagnosticSignal;

public interface MetricsExporter {
    void export(RuntimeSnapshot snapshot);
    default void export(DiagnosticSignal signal) {}
    static MetricsExporter noop() { return snapshot -> {}; }
}
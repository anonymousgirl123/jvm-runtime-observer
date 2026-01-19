package observer.api;

public interface MetricsExporter {
    void export(RuntimeSnapshot snapshot);
    default void export(DiagnosticSignal signal) {}
}
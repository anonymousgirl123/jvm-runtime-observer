package observer.jfr;

import observer.core.RuntimeSnapshot;
import observer.diagnostics.DiagnosticSignal;
import observer.exporter.MetricsExporter;
import jdk.jfr.Event;
import jdk.jfr.Label;

public final class JfrExporter implements MetricsExporter {

    @Override
    public void export(RuntimeSnapshot s) {
        RuntimeEvent e = new RuntimeEvent();
        e.heapUsed = s.heapUsedBytes;
        e.gcPauseMs = s.gcPauseMillis;
        e.cpuLoad = s.processCpuLoad;
        e.commit();
    }

    @Override
    public void export(DiagnosticSignal signal) {
        DiagnosticEvent e = new DiagnosticEvent();
        e.type = signal.type.name();
        e.message = signal.message;
        e.commit();
    }

    static class RuntimeEvent extends Event {
        @Label("Heap Used") long heapUsed;
        @Label("GC Pause ms") long gcPauseMs;
        @Label("CPU Load") double cpuLoad;
    }

    static class DiagnosticEvent extends Event {
        @Label("Type") String type;
        @Label("Message") String message;
    }
}
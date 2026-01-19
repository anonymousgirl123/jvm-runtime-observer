package observer.jfr;

import observer.api.*;
import jdk.jfr.Event;
import jdk.jfr.Label;

public final class JfrExporter implements MetricsExporter {

    @Override
    public void export(RuntimeSnapshot s) {
        RuntimeEvent e = new RuntimeEvent();
        e.heapUsed = s.heapUsedBytes;
        e.gcPauseMs = s.gcPauseMillis;
        e.commit();
    }

    static class RuntimeEvent extends Event {
        @Label("Heap Used") long heapUsed;
        @Label("GC Pause ms") long gcPauseMs;
    }
}
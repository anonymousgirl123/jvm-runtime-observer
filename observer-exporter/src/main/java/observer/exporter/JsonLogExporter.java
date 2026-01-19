package observer.exporter;

import observer.api.*;
import java.time.Instant;
import java.util.logging.Logger;

public final class JsonLogExporter implements MetricsExporter {

    private static final Logger LOG =
            Logger.getLogger("JvmRuntimeObserver");

    @Override
    public void export(RuntimeSnapshot s) {
        LOG.info("{\"type\":\"runtime\",\"ts\":\""
                + Instant.ofEpochMilli(s.timestampMillis)
                + "\",\"heapUsed\":" + s.heapUsedBytes
                + ",\"gcPauseMs\":" + s.gcPauseMillis
                + "}");
    }

    @Override
    public void export(DiagnosticSignal signal) {
        LOG.warning("{\"type\":\"diagnostic\",\"signal\":\""
                + signal.type + "\",\"message\":\""
                + signal.message + "\"}");
    }
}
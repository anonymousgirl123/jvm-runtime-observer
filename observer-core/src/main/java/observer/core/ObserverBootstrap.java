package observer.core;

import observer.diagnostics.DiagnosticsEngine;
import observer.diagnostics.DiagnosticSignal;
import observer.exporter.MetricsExporter;

import java.util.concurrent.*;

final class ObserverBootstrap {

    private static final ScheduledExecutorService EXECUTOR =
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "jvm-runtime-observer");
            t.setDaemon(true);
            return t;
        });

    private static volatile boolean started = false;

    static void bootstrap(MetricsExporter exporter) {
        if (started) return;
        started = true;

        RuntimeSampler sampler = new RuntimeSampler();
        DiagnosticsEngine diagnostics = new DiagnosticsEngine();

        EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                RuntimeSnapshot snapshot = sampler.sample();
                exporter.export(snapshot);

                DiagnosticSignal signal = diagnostics.analyze(snapshot);
                if (signal != null) exporter.export(signal);
            } catch (Exception ignored) {}
        }, 0, 1, TimeUnit.SECONDS);
    }

    static void shutdown() {
        EXECUTOR.shutdownNow();
    }
}
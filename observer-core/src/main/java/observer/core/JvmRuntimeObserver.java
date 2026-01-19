package observer.core;

import observer.api.MetricsExporter;
import observer.api.DiagnosticSignal;
import observer.diagnostics.DiagnosticsEngine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class JvmRuntimeObserver {

    private static final ScheduledExecutorService EXECUTOR =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "jvm-runtime-observer");
                t.setDaemon(true);
                return t;
            });

    private static volatile boolean started = false;

    private JvmRuntimeObserver() {}

    public static void start(MetricsExporter exporter) {
        if (started) return;
        started = true;

        RuntimeSampler sampler = new RuntimeSampler();
        DiagnosticsEngine diagnostics = new DiagnosticsEngine();

        EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                var snapshot = sampler.sample();
                exporter.export(snapshot);

                DiagnosticSignal signal = diagnostics.analyze(snapshot);
                if (signal != null) {
                    exporter.export(signal);
                }
            } catch (Exception ignored) {}
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void stop() {
        EXECUTOR.shutdownNow();
    }
}

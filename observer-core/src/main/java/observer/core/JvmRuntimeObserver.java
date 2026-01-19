package observer.core;

import observer.exporter.MetricsExporter;

public final class JvmRuntimeObserver {

    private JvmRuntimeObserver() {}

    public static void start() {
        start(MetricsExporter.noop());
    }

    public static void start(MetricsExporter exporter) {
        ObserverBootstrap.bootstrap(exporter);
    }

    public static void stop() {
        ObserverBootstrap.shutdown();
    }
}
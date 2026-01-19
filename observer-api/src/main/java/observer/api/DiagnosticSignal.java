package observer.api;

public final class DiagnosticSignal {
    public enum Type { GC_PAUSE_ANOMALY, THREAD_CONTENTION, CPU_PRESSURE }
    public final Type type;
    public final long timestampMillis;
    public final String message;

    public DiagnosticSignal(Type type,long ts,String msg) {
        this.type = type;
        this.timestampMillis = ts;
        this.message = msg;
    }
}
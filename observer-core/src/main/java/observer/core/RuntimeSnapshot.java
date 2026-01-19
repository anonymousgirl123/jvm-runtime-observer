package observer.core;

public final class RuntimeSnapshot {
    public final long timestampMillis;
    public final long heapUsedBytes;
    public final long heapMaxBytes;
    public final long gcPauseMillis;
    public final long gcCount;
    public final int liveThreads;
    public final int blockedThreads;
    public final double processCpuLoad;

    RuntimeSnapshot(long ts, long used, long max, long gcPause, long gcCount,
                    int live, int blocked, double cpu) {
        this.timestampMillis = ts;
        this.heapUsedBytes = used;
        this.heapMaxBytes = max;
        this.gcPauseMillis = gcPause;
        this.gcCount = gcCount;
        this.liveThreads = live;
        this.blockedThreads = blocked;
        this.processCpuLoad = cpu;
    }
}
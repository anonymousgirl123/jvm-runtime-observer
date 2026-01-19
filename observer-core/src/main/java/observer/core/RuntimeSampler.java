package observer.core;

import observer.api.RuntimeSnapshot;
import java.lang.management.*;
import java.util.List;
import com.sun.management.OperatingSystemMXBean;

final class RuntimeSampler {

    private static final MemoryMXBean MEMORY = ManagementFactory.getMemoryMXBean();
    private static final ThreadMXBean THREADS = ManagementFactory.getThreadMXBean();
    private static final List<GarbageCollectorMXBean> GCS =
            ManagementFactory.getGarbageCollectorMXBeans();
    private static final OperatingSystemMXBean OS =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private long lastGcCount;
    private long lastGcTime;

    RuntimeSnapshot sample() {
        MemoryUsage heap = MEMORY.getHeapMemoryUsage();
        int live = THREADS.getThreadCount();
        int blocked = 0;
        for (long id : THREADS.getAllThreadIds()) {
            ThreadInfo info = THREADS.getThreadInfo(id);
            if (info != null && info.getThreadState() == Thread.State.BLOCKED) blocked++;
        }

        long gcCount = 0, gcTime = 0;
        for (GarbageCollectorMXBean gc : GCS) {
            if (gc.getCollectionCount() > 0) gcCount += gc.getCollectionCount();
            if (gc.getCollectionTime() > 0) gcTime += gc.getCollectionTime();
        }

        long deltaCount = gcCount - lastGcCount;
        long deltaTime = gcTime - lastGcTime;
        lastGcCount = gcCount;
        lastGcTime = gcTime;

        return new RuntimeSnapshot(
                System.currentTimeMillis(),
                heap.getUsed(), heap.getMax(),
                deltaTime, deltaCount,
                live, blocked,
                OS.getProcessCpuLoad()
        );
    }
}
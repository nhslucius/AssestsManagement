package sonnh.dev.assetsmanagement.filter;

import org.slf4j.MDC;

public final class TraceContext {

    public static String getTraceId() {
        String traceId = MDC.get("traceId");
        return traceId != null ? traceId : "N/A";
    }
}

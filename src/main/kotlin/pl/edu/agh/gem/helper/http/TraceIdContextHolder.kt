package pl.edu.agh.gem.helper.http

object TraceIdContextHolder {
    private val traceIdHolder = ThreadLocal<String>()

    fun setTraceId(traceId: String) {
        traceIdHolder.set(traceId)
    }

    fun getTraceId(): String? = traceIdHolder.get()

    fun clear() {
        traceIdHolder.remove()
    }
}

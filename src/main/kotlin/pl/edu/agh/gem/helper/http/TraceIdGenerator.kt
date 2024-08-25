package pl.edu.agh.gem.helper.http

import java.util.UUID

object TraceIdGenerator {
    fun generateTraceId(): String {
        return UUID.randomUUID().toString()
    }
}

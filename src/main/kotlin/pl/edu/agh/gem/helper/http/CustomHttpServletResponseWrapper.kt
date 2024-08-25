package pl.edu.agh.gem.helper.http

import jakarta.servlet.ServletOutputStream
import jakarta.servlet.WriteListener
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import kotlin.text.Charsets.UTF_8

class CustomHttpServletResponseWrapper(
    private val response: HttpServletResponse,
) : HttpServletResponseWrapper(response) {

    private val byteArrayOutputStream = ByteArrayOutputStream()

    fun writeOutputStream() {
        response.outputStream.write(byteArrayOutputStream.toByteArray())
    }

    fun getResponsePayload(): String {
        return byteArrayOutputStream.toString(UTF_8.name())
    }

    override fun getOutputStream(): ServletOutputStream {
        return HttpBodyServletOutputStream(
            outputStream = this.byteArrayOutputStream,
        )
    }

    override fun getWriter(): PrintWriter {
        return PrintWriter(
            OutputStreamWriter(
                this.byteArrayOutputStream,
                this.response.characterEncoding,
            ),
        )
    }

    override fun flushBuffer() = writer.flush()

    override fun toString(): String = writer.toString()

    private class HttpBodyServletOutputStream(
        private val outputStream: ByteArrayOutputStream,
    ) : ServletOutputStream() {

        override fun write(b: Int) = outputStream.write(b)

        override fun isReady(): Boolean = true

        override fun setWriteListener(p0: WriteListener?) {}
    }
}

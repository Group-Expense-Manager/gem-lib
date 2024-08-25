package pl.edu.agh.gem.helper.http

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader

class CustomHttpServletRequestWrapper(
    private val request: HttpServletRequest
) : HttpServletRequestWrapper(request) {

    private val body: ByteArray = StreamUtils.copyToByteArray(
            this.request.inputStream
    )

    override fun getInputStream(): ServletInputStream {
        return HttpBodyServletInputStream(
                inputStream = ByteArrayInputStream(body)
        )
    }

    override fun getReader(): BufferedReader {
        return BufferedReader(
                InputStreamReader(
                        ByteArrayInputStream(body)
                )
        )
    }
    
    private class HttpBodyServletInputStream(
        private val inputStream: InputStream
    ) : ServletInputStream() {

        override fun read(): Int = inputStream.read()

        override fun isFinished(): Boolean = inputStream.available() == 0

        override fun isReady(): Boolean = true

        override fun setReadListener(p0: ReadListener?) {}
    }
}

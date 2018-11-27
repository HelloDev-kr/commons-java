package kr.hellodev.support.commons.filter;

import kr.hellodev.support.commons.property.PropertyService;
import kr.hellodev.support.commons.utils.Util;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 11:45 AM
 */
public class ServletFilter implements Filter {
    private static final Logger REQ_DTL = LoggerFactory.getLogger("REQ_DTL");

    @SuppressWarnings("unused")
    private PropertyService propertyService;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();

        propertyService = (PropertyService) autowireCapableBeanFactory.getBean("propertyService");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*HttpServletRequest req = (HttpServletRequest) request;

		// ServletPath
		String path = req.getServletPath();

		if("/common/file_upload.do".equals(path))
		{
			// Log
			{
				StringBuffer log = new StringBuffer();

				log.append("\n=============================================\n");
				log.append("path=" + path + "\n");
				log.append(LogUtil.getParameter(req));
				log.append("ip=" + Util.getIp(req) + "\n");
				log.append("=============================================\n");

				REQ_DTL.info(log.toString());
			}

			chain.doFilter(req, response);
		}
		else
		{
			LogRequestWrapper logRequestWrapper = new LogRequestWrapper(req);
			String body = logRequestWrapper.getBody();

			// Log
			{
				StringBuffer log = new StringBuffer();

				log.append("\n=============================================\n");
				log.append("path=" + path + "\n");
				log.append(LogUtil.getParameter(logRequestWrapper));
				log.append("body=\n\t" + body + "\n");
				log.append("ip=" + Util.getIp(logRequestWrapper) + "\n");
				log.append("=============================================\n");

				REQ_DTL.info(log.toString());
			}

			// set attribute body
			request.setAttribute("___body___", body);

			chain.doFilter(logRequestWrapper, response);
		}
		chain.doFilter(request, response);
		*/
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
        BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
        BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

        // ServletPath
        @SuppressWarnings("unused")
        String path = httpServletRequest.getServletPath();

        // Request Log
        {
            final StringBuilder reqLog = new StringBuilder();
            reqLog.append("\n==========================================================================================");
            reqLog.append("\nRequest - ");
            reqLog.append("\n\tHTTP METHOD:");
            reqLog.append(httpServletRequest.getMethod());
            reqLog.append("\n\tSERVLET PATH:");
            reqLog.append(httpServletRequest.getServletPath());
            reqLog.append("\n\tPATH INFO:");
            reqLog.append(httpServletRequest.getPathInfo());
            reqLog.append("\n\tREQUEST PARAMETERS:");
            reqLog.append(requestMap);

            if (!ServletFileUpload.isMultipartContent(httpServletRequest)) {
                String body = bufferedRequest.getRequestBody();
                reqLog.append("\n\tREQUEST BODY:");
                reqLog.append(body);

                // set attribute body
                bufferedRequest.setAttribute("___body___", body);
            }

            reqLog.append("\n\tREMOTE ADDRESS:");
            reqLog.append(Util.getIp(httpServletRequest));
            reqLog.append("\n==========================================================================================");
            REQ_DTL.info(reqLog.toString());
        }

        chain.doFilter(bufferedRequest, bufferedResponse);
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }

    public static final class BufferedRequestWrapper extends HttpServletRequestWrapper {
        private ByteArrayInputStream bais = null;
        private ByteArrayOutputStream baos;
        private BufferedServletInputStream bsis = null;
        private byte[] buffer;

        public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
            super(req);

            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int letti;
            while ((letti = is.read(buf)) > 0)
                this.baos.write(buf, 0, letti);

            this.buffer = this.baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            this.bais = new ByteArrayInputStream(this.buffer);
            this.bsis = new BufferedServletInputStream(this.bais);
            return this.bsis;
        }

        public String getRequestBody() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
            String line;
            StringBuilder inputBuffer = new StringBuilder();
            do {
                line = reader.readLine();
                if (null != line)
                    inputBuffer.append(line.trim());
            }
            while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }
    }

    private static final class BufferedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {
        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {
        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }

    public class BufferedResponseWrapper implements HttpServletResponse {
        HttpServletResponse original;
        TeeServletOutputStream tee;
        ByteArrayOutputStream bos;

        public BufferedResponseWrapper(HttpServletResponse response) {
            original = response;
        }

        public String getContent() {
            return bos.toString();
        }

        public PrintWriter getWriter() throws IOException {
            return original.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (tee == null) {
                bos = new ByteArrayOutputStream();
                tee = new TeeServletOutputStream(original.getOutputStream(), bos);
            }
            return tee;
        }

        @Override
        public String getCharacterEncoding() {
            return original.getCharacterEncoding();
        }

        @Override
        public String getContentType() {
            return original.getContentType();
        }

        @Override
        public void setCharacterEncoding(String charset) {
            original.setCharacterEncoding(charset);
        }

        @Override
        public void setContentLength(int len) {
            original.setContentLength(len);
        }

        @Override
        public void setContentLengthLong(long length) {
            original.setContentLengthLong(length);
        }

        @Override
        public void setContentType(String type) {
            original.setContentType(type);
        }

        @Override
        public void setBufferSize(int size) {
            original.setBufferSize(size);
        }

        @Override
        public int getBufferSize() {
            return original.getBufferSize();
        }

        @Override
        public void flushBuffer() throws IOException {
            if (tee == null)
                return;
            tee.flush();
        }

        @Override
        public void resetBuffer() {
            original.resetBuffer();
        }

        @Override
        public boolean isCommitted() {
            return original.isCommitted();
        }

        @Override
        public void reset() {
            original.reset();
        }

        @Override
        public void setLocale(Locale loc) {
            original.setLocale(loc);
        }

        @Override
        public Locale getLocale() {
            return original.getLocale();
        }

        @Override
        public void addCookie(Cookie cookie) {
            original.addCookie(cookie);
        }

        @Override
        public boolean containsHeader(String name) {
            return original.containsHeader(name);
        }

        @Override
        public String encodeURL(String url) {
            return original.encodeURL(url);
        }

        @Override
        public String encodeRedirectURL(String url) {
            return original.encodeRedirectURL(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeUrl(String url) {
            return original.encodeUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeRedirectUrl(String url) {
            return original.encodeRedirectUrl(url);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            original.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            original.sendError(sc);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            original.sendRedirect(location);
        }

        @Override
        public void setDateHeader(String name, long date) {
            original.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date) {
            original.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value) {
            original.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            original.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value) {
            original.setIntHeader(name, value);
        }

        @Override
        public void addIntHeader(String name, int value) {
            original.addIntHeader(name, value);
        }

        @Override
        public void setStatus(int sc) {
            original.setStatus(sc);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setStatus(int sc, String sm) {
            original.setStatus(sc, sm);
        }

        @Override
        public String getHeader(String name) {
            return original.getHeader(name);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return original.getHeaderNames();
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return original.getHeaders(name);
        }

        @Override
        public int getStatus() {
            return original.getStatus();
        }
    }
}

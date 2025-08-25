package lutfiangg20.demo_teknis.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogginFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

    Map<String, Object> logMap = new HashMap<>();
    logMap.put("method", request.getMethod());
    logMap.put("path", request.getRequestURI());
    logMap.put("query", request.getQueryString());

    Map<String, String> headersMap = new HashMap<>();
    wrappedRequest.getHeaderNames().asIterator()
        .forEachRemaining(header -> headersMap.put(header, request.getHeader(header)));
    logMap.put("headers", headersMap);

    filterChain.doFilter(wrappedRequest, wrappedResponse);

    var requestBody = wrappedRequest.getContentAsByteArray();
    if (requestBody.length > 0) {
      logMap.put("body", new String(requestBody, request.getCharacterEncoding()));
    }

    logger.info("Request: " + objectMapper.writeValueAsString(logMap));

    Map<String, Object> responseLog = new HashMap<>();
    responseLog.put("status", response.getStatus());
    var responseBody = wrappedResponse.getContentAsByteArray();
    if (responseBody.length > 0) {
      responseLog.put("body", new String(responseBody, wrappedResponse.getCharacterEncoding()));
    }
    logger.info("Response: " + objectMapper.writeValueAsString(responseLog));

    wrappedResponse.copyBodyToResponse();
  }

}

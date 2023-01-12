package gbennett.autotodo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * This class is an integral part in the statefulRestTemplate bean saving cookies between requests.
 */
@Slf4j
public class StatefulRestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private List<String> cookies;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
          throws IOException {
    if (cookies != null && !cookies.isEmpty()) {
      cookies.forEach(c -> request.getHeaders().add(HttpHeaders.COOKIE, c));
      log.info("Set cookies {}", cookies);
    }
    ClientHttpResponse response = execution.execute(request, body);

    if (cookies == null) {
      cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
    }
    return response;
  }

  // Used for unit tests
  List<String> getCookies() {
    return cookies;
  }
}

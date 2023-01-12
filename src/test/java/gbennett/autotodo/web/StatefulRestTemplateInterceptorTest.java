package gbennett.autotodo.web;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatefulRestTemplateInterceptorTest {

  @Mock
  private HttpRequest request;

  @Mock
  private ClientHttpRequestExecution execution;

  @Mock
  private ClientHttpResponse response;

  private final StatefulRestTemplateInterceptor interceptor = new StatefulRestTemplateInterceptor();

  @Test
  @Order(1)
  @SneakyThrows
  void interceptorSavesCookiesFromResponse() {
    when(execution.execute(any(), any())).thenReturn(response);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, "my-cookie");
    when(response.getHeaders()).thenReturn(headers);

    interceptor.intercept(request, new byte[1], execution);

    List<String> cookies = interceptor.getCookies();
    assertThat(cookies)
            .hasSize(1)
            .contains("my-cookie");
  }

  @Test
  @Order(2)
  @SneakyThrows
  void interceptorSetsCookiesOnRequest() {
    when(execution.execute(any(), any())).thenReturn(response);
    when(request.getHeaders()).thenReturn(new HttpHeaders());

    interceptor.intercept(request, new byte[1], execution);

    List<String> cookies = request.getHeaders().get(HttpHeaders.COOKIE);
    assertThat(cookies)
            .hasSize(1)
            .contains("my-cookie");
  }
}
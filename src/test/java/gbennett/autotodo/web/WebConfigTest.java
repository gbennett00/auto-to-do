package gbennett.autotodo.web;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class WebConfigTest {

  @Test
  void ensureRestTemplateIsConfiguredToBeStateful() {
    RestTemplate restTemplate = new WebConfig().statefulRestTemplate();
    assertThat(restTemplate.getInterceptors())
            .hasSize(1)
            .hasOnlyElementsOfType(StatefulRestTemplateInterceptor.class);
  }

}
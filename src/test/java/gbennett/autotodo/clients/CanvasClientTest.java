package gbennett.autotodo.clients;

import gbennett.autotodo.io.SecretManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CanvasClientTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private SecretManager secretManager;

  private CanvasClient client;

  @BeforeEach
  void setUp() {
    client = new CanvasClient(restTemplate, secretManager);
  }

  @Test
  @SneakyThrows
  @SuppressWarnings("rawtypes")
  void testGetToDoList() {
    when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
            .thenReturn(new ResponseEntity<>("body", HttpStatus.OK));
    when(secretManager.getCanvasToken()).thenReturn("my-token");

    client.getToDoList();

    ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
    verify(restTemplate)
            .exchange(eq(CanvasClient.CANVAS_TO_DO_URI), eq(HttpMethod.GET), captor.capture(), eq(String.class));
    HttpEntity entity = captor.getValue();
    assertThat(entity.getHeaders())
            .hasSize(1)
            .containsEntry(HttpHeaders.AUTHORIZATION, List.of("Bearer my-token"));
  }

}
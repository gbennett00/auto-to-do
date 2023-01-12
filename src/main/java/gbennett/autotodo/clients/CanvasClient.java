package gbennett.autotodo.clients;

import gbennett.autotodo.io.SecretManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Retrieves a user's to-do list from the Canvas API using a bearer token found on the local machine.
 */
@Component
public class CanvasClient {

  static final String CANVAS_TO_DO_URI = "https://byu.instructure.com/api/v1/users/self/todo";

  private final RestTemplate restTemplate;
  private final SecretManager secretManager;

  public CanvasClient(RestTemplate restTemplate, SecretManager secretManager) {
    this.restTemplate = restTemplate;
    this.secretManager = secretManager;
  }

  public String getToDoList() throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + secretManager.getCanvasToken());

    ResponseEntity<String> response = restTemplate.exchange(CANVAS_TO_DO_URI,
            HttpMethod.GET, new HttpEntity<>(headers), String.class);
    return response.getBody();
  }

}

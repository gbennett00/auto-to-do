package gbennett.autotodo.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SecretManagerTest {

  @Test
  void secretManagerThrowsExceptionWithBadFilePath() {
    SecretManager manager = new SecretManager("bad-pattern");
    
    assertThrows(IOException.class, manager::getCanvasToken);
  }
  
  @Test
  @SneakyThrows
  void secretManagerReturnsCanvasTokenFromFile() {
    SecretManager manager = new SecretManager("src,test,resources,test-props.txt");

    String token = manager.getCanvasToken();

    assertThat(token).isEqualTo("my-secret");
  }

}
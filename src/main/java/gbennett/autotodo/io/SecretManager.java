package gbennett.autotodo.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * This class retrieves properties saved on the local machine.
 */
@Component
@Slf4j
public class SecretManager {

  private final String commaDelimitedPath;
  private String canvasToken;

  public SecretManager(@Value("${property-file-location}") String commaDelimitedPath) {
    this.commaDelimitedPath = commaDelimitedPath;
  }

  public String getCanvasToken() throws IOException {
    if (canvasToken == null) readPropertiesFile();
    return canvasToken;
  }

  private void readPropertiesFile() throws IOException {
    try (Reader propReader = Files.newBufferedReader(Path.of(commaDelimitedPath.replace(",", File.separator)))) {
      Properties props = new Properties();
      props.load(propReader);
      canvasToken = (String) props.get("canvasToken");
    } catch (IOException io) {
      log.error("Error reading property file");
      throw io;
    }
  }
}

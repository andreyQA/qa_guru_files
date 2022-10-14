import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonParserTest {
    ClassLoader cl = TryParseFilesTest.class.getClassLoader();
    @DisplayName("Check JSON file")
    @Test
    void jsonTest()  throws Exception {
        try (InputStream jsonStream = cl.getResourceAsStream("testFile.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonTemplate jsonFile = objectMapper.readValue(jsonStream, JsonTemplate.class);
            assertThat(jsonFile.dishname).contains("stew");
            assertThat(jsonFile.availability).isEqualTo("anywhere");
            assertThat(jsonFile.difficult).isEqualTo("medium");
            assertThat(jsonFile.price).isEqualTo(300);
            assertThat(jsonFile.ingridients.meat).isTrue();
            assertThat(jsonFile.ingridients.oil).isTrue();
            assertThat(jsonFile.ingridients.pepper).isTrue();
            assertThat(jsonFile.ingridients.salt).isTrue();
            assertThat(jsonFile.ingridients.vegetables).isTrue();
        }
    }
}

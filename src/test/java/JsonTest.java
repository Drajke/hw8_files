import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    ClassLoader cl = JsonTest.class.getClassLoader();

    @Test
    public void parseJsonFile() throws Exception {

        try(InputStream resource = cl.getResourceAsStream("json.json");
            InputStreamReader reader = new InputStreamReader(resource)
        ) {
            ObjectMapper mapper = new ObjectMapper();
            Client client = mapper.readValue(reader, Client.class);

            assertThat(client.name).contains("John");
            assertThat(client.age).contains("over");
            assertThat(client.account).contains("40802830000000000000");
            assertThat(client.phone).isEqualTo("9211234567");
            assertThat(client.inn).isEqualTo(1234567890);
        }
    }
}

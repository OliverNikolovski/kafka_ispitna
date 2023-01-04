package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class VmMessageDeserializer implements Deserializer<VmMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public VmMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, VmMessage.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

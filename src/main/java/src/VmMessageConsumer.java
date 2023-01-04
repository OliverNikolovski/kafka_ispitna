package src;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class VmMessageConsumer implements Runnable {

    private final KafkaConsumer<String, VmMessage> consumer;
    private final String name;
    private static final String VM_TOPIC = "vm-topic";

    public VmMessageConsumer(Properties properties, String name) {
        consumer = new KafkaConsumer<>(properties);
        this.name = name;
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList(VM_TOPIC));
        while (true) {
            ConsumerRecords<String, VmMessage> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, VmMessage> record : records) {
                executeVm(record);
            }
        }
    }

    public void executeVm(ConsumerRecord<String, VmMessage> record) {
        try {
            VmMessage value = record.value();
            System.out.printf("Consumer=%s; Value=%s; Key=%s; Topic=%s; Partition=%d%s", name, value, record.key(), record.topic(), record.partition(), System.lineSeparator());
            Thread.sleep((long)(value.getExecutionTime() * 1000L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

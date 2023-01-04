package src;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties producerProps = getProducerProperties();
        Properties consumerProps = getConsumerProperties();

        Thread producer = new Thread(new VmMessageProducer(producerProps));
        Thread consumer1 = new Thread(new VmMessageConsumer(consumerProps, "Consumer A"));
        Thread consumer2 = new Thread(new VmMessageConsumer(consumerProps, "Consumer B"));

        List<Thread> threads = List.of(producer, consumer1, consumer2);
        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static Properties getProducerProperties() {
        Properties properties = getCommonProperties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VmMessageSerializer.class);
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, VmMessagePartitioner.class);
        return properties;
    }

    private static Properties getConsumerProperties() {
        Properties properties = getCommonProperties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VmMessageDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "vmConsumerGroup");
        return properties;
    }

    private static Properties getCommonProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return properties;
    }

}

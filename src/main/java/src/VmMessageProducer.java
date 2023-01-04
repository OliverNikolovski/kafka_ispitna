package src;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;

public class VmMessageProducer implements Runnable {

    private final KafkaProducer<String, VmMessage> producer;
    private final Random random;
    private static final String VM_TOPIC = "vm-topic";

    public VmMessageProducer(Properties properties) {
        producer = new KafkaProducer<>(properties);
        random = new Random();
    }

    // if you want to write the messages yourself using the command line, use this `run` method
//    @Override
//    public void run() {
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            String input = scanner.nextLine();
//            VmMessage message = VmMessage.of(input);
//            ProducerRecord<String, VmMessage> record = new ProducerRecord<>(VM_TOPIC, message.getType().name(), message);
//            producer.send(record);
//        }
//        producer.close();
//    }


    // this `run` method generates a million random messages which are sent to the kafka broker
    @Override
    public void run() {
        IntStream.range(0, 1_000_000)
                .mapToObj(i -> getRandomVmMessage())
                .map(message -> new ProducerRecord<>(VM_TOPIC, message.getType().name(), message))
                .forEach(producer::send);
        producer.close();
    }

    public VmMessage getRandomVmMessage() {
        VmType type = random.nextBoolean() ? VmType.COMPUTE : VmType.STORAGE;
        int ram = random.nextInt(1000, 20000);
        int cores = random.nextInt(1, 24);
        double executionTime = random.nextDouble(0.4, 5);
        return new VmMessage(type, ram, cores, executionTime);
    }
}

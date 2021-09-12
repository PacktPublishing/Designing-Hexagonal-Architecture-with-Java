package dev.davivieira.framework.adapters.output.kafka;

import dev.davivieira.application.ports.output.NotifyEventOutputPort;
import dev.davivieira.framework.adapters.input.rest.RouterNetworkRestAdapter;
import dev.davivieira.framework.adapters.input.websocket.WebSocketClientAdapter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class NotifyEventKafkaAdapter implements NotifyEventOutputPort {

    private static NotifyEventKafkaAdapter instance;

    private static KafkaProducer<Long, String> producer;

    private static KafkaConsumer<Long, String> consumer;

    private static String KAFKA_BROKERS = "localhost:9092";

    private static String GROUP_ID_CONFIG="consumerGroup1";

    private static String CLIENT_ID="hexagonal-client";

    private static String TOPIC_NAME="topology-inventory-events";

    private static String OFFSET_RESET_EARLIER="earliest";

    private  static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

    private static boolean sendToWebsocket;

    private static Producer<Long, String> createProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);
        return new KafkaProducer<>(props);
    }

    public static Consumer<Long, String> createConsumer(){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_RESET_EARLIER);

        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));
        return consumer;
    }

    @Override
    public void sendEvent(String eventMessage){
        var record = new ProducerRecord<Long, String>(
                TOPIC_NAME, eventMessage);
        try {
            var metadata = producer.send(record).get();
            System.out.println("Event message " +
                    "sent to the topic "+TOPIC_NAME+": "
                    +eventMessage+".");
            getEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEvent(){
        int noMessageToFetch = 0;
        AtomicReference<String> event = new AtomicReference<>("");
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(5);
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
                }
            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
                event.set(record.value());
            });
        }
        var eventMessage = event.toString();
        if(sendToWebsocket)
        sendMessage(eventMessage);
        return eventMessage;
    }

    public void sendMessage(String message){
        try {
            var client = new WebSocketClientAdapter(new URI("ws://localhost:8887"));
            client.connectBlocking();
            client.send(message);
            client.closeBlocking();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static NotifyEventKafkaAdapter getInstance(){
        if (instance == null) {
            instance = new NotifyEventKafkaAdapter();
        }
        sendToWebsocket = true;
        producer = (KafkaProducer<Long, String>) createProducer();
        consumer = (KafkaConsumer<Long, String>) createConsumer();
        return instance;
    }
}
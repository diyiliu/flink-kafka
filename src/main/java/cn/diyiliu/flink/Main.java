package cn.diyiliu.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Main
 *
 * @author: DIYILIU
 * @date: 2021/12/18
 */
public class Main {

    static String TOPIC_IN = "fleet-rawdata_up";
    static String TOPIC_OUT = "fleet-rawdata_test";
    static String BOOTSTRAP_SERVER = "192.168.1.171:9092";

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", BOOTSTRAP_SERVER);
        properties.setProperty("group.id", "test");

        FlinkKafkaConsumer<KafkaRecord> myConsumer = new FlinkKafkaConsumer(TOPIC_IN, new MySchema(), properties);
//        myConsumer.setStartFromEarliest();     // 尽可能从最早的记录开始
        myConsumer.setStartFromLatest();       // 从最新的记录开始


        // Create Kafka producer from Flink API
        Properties prodProps = new Properties();
        prodProps.put("bootstrap.servers", BOOTSTRAP_SERVER);

        FlinkKafkaProducer<String> kafkaProducer =
                new FlinkKafkaProducer<>(TOPIC_OUT,
                        ((value, timestamp) -> new ProducerRecord(TOPIC_OUT, "myKey".getBytes(), value.getBytes())),
                        prodProps,
                        FlinkKafkaProducer.Semantic.EXACTLY_ONCE);


        DataStream<KafkaRecord> stream = env.addSource(myConsumer);

        DataStream outStream = stream.keyBy(KafkaRecord::getKey)
                .process(new RecordProcess())
                .name("test-process");

        outStream.addSink(kafkaProducer)
                .name("test-producer");

        env.execute("kafka test");
    }
}

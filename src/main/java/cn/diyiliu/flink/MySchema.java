package cn.diyiliu.flink;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * MySchema
 *
 * @author: DIYILIU
 * @date: 2021/12/20
 */
public class MySchema implements KafkaDeserializationSchema<KafkaRecord> {


    @Override
    public boolean isEndOfStream(KafkaRecord nextElement) {
        return false;
    }

    @Override
    public KafkaRecord deserialize(ConsumerRecord<byte[], byte[]> record) {
        String key = new String(record.key());
        String value = new String(record.value());
        long time = record.timestamp();

        KafkaRecord data = new KafkaRecord();
        data.setKey(key);
        data.setValue(value);
        data.setTime(time);

        return data;
    }

    @Override
    public TypeInformation<KafkaRecord> getProducedType() {
        return TypeInformation.of(KafkaRecord.class);
    }
}

package cn.diyiliu.flink;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * RecordProcess
 *
 * @author: DIYILIU
 * @date: 2021/12/20
 */
public class RecordProcess extends KeyedProcessFunction<String, KafkaRecord, String> {


    @Override
    public void processElement(KafkaRecord data, KeyedProcessFunction<String, KafkaRecord, String>.Context ctx, Collector<String> out) throws Exception {
        System.out.println(data.getKey() + ":" + data.getValue());

        out.collect(data.getKey());
    }
}

package cn.diyiliu.flink;

import java.io.Serializable;

/**
 * KafkaRecord
 *
 * @author: DIYILIU
 * @date: 2021/12/20
 */
public class KafkaRecord implements Serializable {

    private String key;
    private String value;
    private Long time;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

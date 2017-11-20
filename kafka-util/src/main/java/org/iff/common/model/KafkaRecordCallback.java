package org.iff.common.model;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Serializable;

/**
 * Created by thangpham on 20/11/2017.
 */
public abstract class KafkaRecordCallback implements Callback, Serializable {

    private KafkaRecord kafkaRecord;

    public KafkaRecordCallback(KafkaRecord kafkaRecord) {
        this.kafkaRecord = kafkaRecord;
    }

    public abstract void onCompletion(KafkaRecord log, RecordMetadata metadata, Exception exception);

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        onCompletion(kafkaRecord, metadata, exception);
    }

    public KafkaRecord getKafkaRecord() {
        return kafkaRecord;
    }

    public void setKafkaRecord(KafkaRecord kafkaRecord) {
        this.kafkaRecord = kafkaRecord;
    }

}
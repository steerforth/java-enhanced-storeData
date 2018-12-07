package com.jpz.dcim.poseidon.common.kafka;

import java.io.Closeable;
import java.util.Properties;

/**
 * @Program: poseidon
 * @Author: Steerforth
 * @Description: kafka创建对象工厂类
 * @Date: 2018-12-07 09:47
 */
public abstract class KafkaFactory {
    public final Closeable create(){
        Properties properties = this.prepareProperties();
        return this.create(properties);
    }

    protected abstract Closeable create(Properties properties);

    protected abstract Properties prepareProperties();

}

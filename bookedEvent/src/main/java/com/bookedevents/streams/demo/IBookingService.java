package com.bookedevents.streams.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface IBookingService {

     //Just the min req vars
    BiFunction<Serializer<Booking>, String, DefaultKafkaProducerFactory<UUID, Booking>> bookingJsonSerdeFactoryFunction
            = (bookingSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
            ProducerConfig.RETRIES_CONFIG, 0,
            ProducerConfig.BATCH_SIZE_CONFIG, 16384,
            ProducerConfig.LINGER_MS_CONFIG, 1,
            ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, bookingSerde.getClass()));

}

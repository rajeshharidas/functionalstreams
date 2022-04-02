package com.bookedevents.streams.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Serde<Booking> bookingJsonSerde() {
        return new JsonSerde<>(Booking.class, new ObjectMapper());
    }

}

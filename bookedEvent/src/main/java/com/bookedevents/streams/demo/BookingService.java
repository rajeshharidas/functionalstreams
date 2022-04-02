package com.bookedevents.streams.demo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final InteractiveQueryService interactiveQueryService;

    private final Serde<Booking> bookingJsonSerde;

    @Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
    private String bootstrapServer;

    @Value("${spring.cloud.stream.bindings.bookingProcess-in-0.destination}")
    private String bookingTopic;

    public Function<Booking, Booking> placeBooking() {
        return bookingIn -> {
            //create a booking
            var booking = Booking.builder()//
                    .facilityName(bookingIn.getFacilityName())//
                    .bookingUuid(UUID.randomUUID())//
                    .bookingStatus(BookingStatus.PENDING)//
                    .build();

            //producer
            new KafkaTemplate<>(bookingJsonSerdeFactoryFunction
                    .apply(bookingJsonSerde.serializer(), bootstrapServer), true) {{
                setDefaultTopic(bookingTopic);
                sendDefault(booking.getBookingUuid(), booking);
            }};
            return booking;
        };
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Function<KStream<UUID, Booking>, KStream<UUID, Booking>> bookingProcess() {
               return input -> input.peek((uuid, booking) -> booking.setBookingStatus(BookingStatus.RECEIVED))
				    .peek((key, value) -> log.debug("Routing Booking: {} [status: {}]", key, value.getBookingStatus()))
		                    .map(KeyValue::new);
    }

}

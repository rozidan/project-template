package com.company.template.server.config;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.server.domain.model.Product;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Idan Rozenfeld
 */
@Configuration
@EnableKafka
@Profile("staging")
public class KafkaConfig {

    public static final String PRODUCT_MESSAGE_CONTAINER = "productsContainer";

    private String brokers;
    private String appName;

    public KafkaConfig(@Value("${template.kafka.brokers}") String brokers,
                       @Value("${spring.application.name}") String appName) {
        this.brokers = brokers;
        this.appName = appName;
    }

    public ProducerFactory<String, ProductDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<String, ProductDto> productsTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name = PRODUCT_MESSAGE_CONTAINER)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ProductDto>> productsContainer() {
        ConcurrentKafkaListenerContainerFactory<String, ProductDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

    public ConsumerFactory<String, ProductDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProductJsonDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, appName + " #product");
        propsMap.put(ConsumerConfig.CLIENT_ID_CONFIG, appName);
        return propsMap;
    }

    public static class ProductJsonDeserializer extends NewJsonDeserializer<Product> {
    }

    public static class NewJsonDeserializer<T> extends JsonDeserializer<T> {

        @Override
        public T deserialize(String topic, byte[] data) {
            try {
                return super.deserialize(topic, data);
            } catch (SerializationException ex) {
                return null;
            }
        }
    }
}

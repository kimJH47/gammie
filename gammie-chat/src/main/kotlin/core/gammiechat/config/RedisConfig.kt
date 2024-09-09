package core.gammiechat.config

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {
    @Bean
    @Primary
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisProperties = RedisProperties()
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            this.connectionFactory = redisConnectionFactory()
            this.keySerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
        }
    }

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        val redisProperties = RedisProperties()
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = GenericJackson2JsonRedisSerializer()

        val serializationContext = RedisSerializationContext
            .newSerializationContext<String, Any>(keySerializer)
            .key(keySerializer)
            .value(valueSerializer)
            .hashKey(keySerializer)
            .hashValue(valueSerializer)
            .build()

        return ReactiveRedisTemplate(factory, serializationContext)
    }

    @Bean
    fun redisClient(): RedisClient {
        val redisProperties = RedisProperties()
        return RedisClient.create(
            RedisURI.builder()
                .withHost(redisProperties.host)
                .withPort(redisProperties.port)
                .build()
        )
    }
}
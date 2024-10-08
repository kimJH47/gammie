package core.gammiechat.config

import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableConfigurationProperties(RedisProperties::class)
class RedisConfig(
    private val redisProperties: RedisProperties
) {
    @Bean
    @Primary
    fun redisConnectionFactory(): RedisConnectionFactory {
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
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisSerializationContext(): RedisSerializationContext<String, Any> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = GenericJackson2JsonRedisSerializer()

        return RedisSerializationContext
            .newSerializationContext<String, Any>(keySerializer)
            .key(keySerializer)
            .value(valueSerializer)
            .hashKey(keySerializer)
            .hashValue(valueSerializer)
            .build()
    }

    @Bean
    fun reactiveRedisTemplate(
        factory: ReactiveRedisConnectionFactory,
        redisSerializationContext: RedisSerializationContext<String, Any>
    ): ReactiveRedisTemplate<String, Any> {
        return ReactiveRedisTemplate(factory, redisSerializationContext)
    }

    @Bean
    fun reactiveRedisMessageListenerContainer(factory: ReactiveRedisConnectionFactory): ReactiveRedisMessageListenerContainer {
        return ReactiveRedisMessageListenerContainer(factory)
    }
}
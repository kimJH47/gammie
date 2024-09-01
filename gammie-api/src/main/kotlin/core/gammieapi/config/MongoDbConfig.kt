package core.gammieapi.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableMongoRepositories(
    basePackages = ["core.gammieapi.repository"],
)
@EnableReactiveMongoRepositories(
    basePackages = ["core.gammieapi.repository"],
)
class MongoDbConfig
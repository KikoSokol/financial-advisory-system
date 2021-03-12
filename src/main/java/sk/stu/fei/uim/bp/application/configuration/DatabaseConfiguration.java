package sk.stu.fei.uim.bp.application.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


@Configuration
@Profile("main")
public class DatabaseConfiguration
{

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @Bean
    public MongoClient mongoClient()
    {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public MongoTemplate mongoTemplate()
    {
        return new MongoTemplate(mongoClient(),"financial_advisory_system");
    }

    @Bean
    public GridFsTemplate gridFsTemplate()
    {
        return new GridFsTemplate(mongoDbFactory(),mappingMongoConverter);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory()
    {
        return new SimpleMongoClientDatabaseFactory(mongoClient(),"financial_advisory_system");
    }


}

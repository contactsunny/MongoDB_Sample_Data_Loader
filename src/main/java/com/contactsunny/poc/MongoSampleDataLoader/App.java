package com.contactsunny.poc.MongoSampleDataLoader;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

         String mongoConnectionString, mongoDatabaseName, mongoCollectionName;
         int documentCount;

         if (args.length < 4) {
             logger.error("Invalid arguments");
             System.exit(1);
         }

         mongoConnectionString = args[0];
         mongoDatabaseName = args[1];
         mongoCollectionName = args[2];
         documentCount = Integer.parseInt(args[3]);

        mongoConnectionString = mongoConnectionString + "/" + mongoDatabaseName;

        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(new MongoClientURI(mongoConnectionString));
        mongoTemplate = new MongoTemplate(simpleMongoDbFactory);

        for (int index = 0; index < documentCount; index++) {
            Document document = new Document();
            document.put("string1", getAlphaNumericString(10));
            document.put("string2", getAlphaNumericString(20));
            document.put("double1", getRandomDoubleBetweenRange(0, 100));
            document.put("double2", getRandomDoubleBetweenRange(100, 1000));

            mongoTemplate.getDb().getCollection(mongoCollectionName).insertOne(document);

            logger.info("Inserted " + (index + 1) + " documents!");
        }
    }

    private static String getAlphaNumericString(int stringLength) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(stringLength);

        for (int loopIndex = 0; loopIndex < stringLength; loopIndex++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }

    private static double getRandomDoubleBetweenRange(double min, double max) {
        double randomNumber = (Math.random()*((max-min)+1))+min;
        return randomNumber;
    }
}

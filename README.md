# MongoDB Sample Data Loader

## How to run

The program takes four arguments:

1. MongoDB connection string
2. MongoDB database name
3. Target collection name
4. Number of records to generate

## Command

```shell script
java -jar MongoSampleDataLoader.java mongodb://localhost:27017 drill sampleData 10000
```
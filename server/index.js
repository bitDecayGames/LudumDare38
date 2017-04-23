const express = require('express');
const logger = require('winston');
const MongoClient = require('mongodb').MongoClient

const port = process.env.PORT || 8080;
const mongoDB = 'ld38';
const mongoUrl = process.env.MONGO_URL || `mongodb://localhost:27017/${mongoDB}`;

const app = express();

app.listen(port, () => {
    logger.info(`Listening on ${port}`);

    logger.info(`Connecting to mongo: ${mongoUrl} ...`);
    MongoClient.connect(mongoUrl)
        .then((db) => {
            logger.info(`Connected to mongo db ${mongoDB}`);
        })
        .catch((err) => {
            logger.error(`Error connecting to mongo`, err);
        });
});
const express = require('express');
const logger = require('winston');
const MongoClient = require('mongodb').MongoClient

const port = process.env.PORT || 8080;
const mongoDB = 'ld38';
const mongoUrl = process.env.MONGO_URL || `mongodb://localhost:27017/${mongoDB}`;

const scoreUrl = '/score';

const connectDb = (url) => {
    logger.info(`Connecting to mongo: ${url} ...`);
    return MongoClient.connect(mongoUrl)
        .then((db) => {
            logger.info(`Connected to mongo db ${mongoDB}`);
            return db;
        })
        .catch((err) => {
            logger.error(`Error connecting to mongo`, err);
            throw err;
        });
}

const postScore = (req, res) => {
    const { name, score } = req.body;

    connectDb(mongoUrl)
        .then((db) => {

        })
        .catch((err) => {
            logger.error(err);
            res.status(500).end();
        })
}

const getScores = (req, res) => {
    res.status(200).json({
        status: 'ok'
    });
};

const app = express();

app.get(scoreUrl, getScores);
app.post(scoreUrl, postScore);

app.listen(port, () => {
    logger.info(`Listening on ${port}`);
});


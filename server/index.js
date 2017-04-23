const express = require('express');
const logger = require('winston');
const expressWinston = require('express-winston');
const bodyParser = require('body-parser');
const MongoClient = require('mongodb').MongoClient

const port = process.env.PORT || 8080;
const mongoDB = 'ld38';
const mongoUrl = process.env.MONGO_URL || `mongodb://localhost:27017/${mongoDB}`;

const scoreUrl = '/score';

const connectDb = (url) => {
    return MongoClient.connect(url)
        .then((db) => db)
}

const postScore = (req, res) => {
    const { name, score } = req.body;

    connectDb(mongoUrl)
        .then((db) => {
            const scores = db.collection('scores');
            const newScore = {
                name,
                score,
                time: Date.now()
            };

            return scores.insert(newScore)
                .then((result) => {
                    db.close();
                    res.status(200).end();
                });
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

app.use(expressWinston.logger({
    transports: [
        new logger.transports.Console({
            json: true,
            colorize: true
        })
    ]
}));
app.use(bodyParser.json());

app.get(scoreUrl, getScores);
app.post(scoreUrl, postScore);

app.use(expressWinston.errorLogger({
    transports: [
        new logger.transports.Console({
            json: true,
            colorize: true
        })
    ]
}));

app.listen(port, () => {
    logger.info(`Listening on ${port}`);
});


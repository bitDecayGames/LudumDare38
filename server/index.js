const express = require('express');
const logger = require('winston');
const expressWinston = require('express-winston');
const bodyParser = require('body-parser');
const MongoClient = require('mongodb').MongoClient

const port = process.env.PORT || 8080;
const apiKey = process.env.API_KEY || 'e1103cee83aee47220fc82e30c37143a';
const mongoDB = 'ld38';
const mongoUrl = process.env.MONGO_URL || `mongodb://localhost:27017/${mongoDB}`;

const scoreUrl = '/score';

const authMiddleware = (req, res, next) => {
    const authorization = req.get('Authorization');
    if (authorization !== apiKey) {
        res.status(401).end();
    } else {
        next();
    }
}

const connectDb = (url) => {
    return MongoClient.connect(url)
        .then((db) => db)
}

const postScore = (req, res) => {
    let { name, score } = req.body;
    score = parseInt(score);

    connectDb(mongoUrl)
        .then((db) => {
            const scores = db.collection('scores');
            const newScore = {
                name,
                score,
                time: Date.now()
            };

            return scores.insert(newScore)
                .then(() => {
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
    let num = req.query.num || 10;
    num = parseInt(num);

    connectDb(mongoUrl)
        .then((db) => {
            const scores = db.collection('scores');

            return scores.find({}, {
                    name: 1, score: 1, time: 1, _id: 0 }
                ).sort({ score: -1 })
                .limit(num)
                .toArray()
                    .then((results) => {
                        db.close();

                        res.status(200).json({
                            scores: results
                        });
                    });
        })
        .catch((err) => {
            logger.error(err);
            res.status(500).end();
        })
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
app.use(authMiddleware);

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


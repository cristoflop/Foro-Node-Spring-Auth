"use strict"

const express = require('express');
const database = require('./database.js');
const topicRouter = require('./routes/topicRouter.js');
const userRouter = require('./routes/userRouter.js');
const fs = require('fs');
const https = require('https');

const PORT = require('./config').port;

const app = express();

app.use(express.json());

app.use('/api', topicRouter);
app.use('/api', userRouter);

async function main() {

    await database.connect();

    https.createServer({
        key: fs.readFileSync('src/server.key'),
        cert: fs.readFileSync('src/server.cert')
    }, app).listen(PORT, () => {
        console.log('Forum https server listening on port ' + PORT);
    })

    process.on('SIGINT', () => {
        database.disconnect();
        console.log('Process terminated');
        process.exit(0);
    });
}

main();
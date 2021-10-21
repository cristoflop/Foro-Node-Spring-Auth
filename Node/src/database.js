const mongoose = require('mongoose');
const config = require('./config');
const url = `mongodb://${config.dbConfig.host}:${config.dbConfig.port}/${config.dbConfig.database}`;

const User = require('./models/user.js').User;
const Topic = require('./models/topic.js').Topic;
const Message = require('./models/message').Message


async function connect() {

    await mongoose.connect(url, {
        useUnifiedTopology: true,
        useNewUrlParser: true,
        useFindAndModify: false
    });

    console.log("Connected to Mongo");

    await init();
}

async function disconnect() {
    await mongoose.connection.close();
    console.log("Disconnected from MongoDB")
}

async function init() {

    console.log('Initializing database');

    console.log('Populating database with users');

    await User.deleteMany({});

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed"),
        nick: "user1",
        password: "password",
        email: "user1@email.es",
        admin: true
    }).save();

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bef"),
        nick: "user2",
        password: "pass",
        email: "user2@email.es",
        admin: false
    }).save();

    console.log('Populating database with topics');

    await Topic.deleteMany({});

    await new Topic({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bf0"),
        title: "Topic 1",
        creator: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    }).save();

    await new Topic({
        _id: new mongoose.Types.ObjectId("5fda350d3749aa4832165b84"),
        title: "Topic 2",
        creator: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    }).save();

    console.log('Populating database with messages');

    await new Message({
        value: "Message 1",
        topic: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bf0"),
        creator: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bef")
    }).save();

    await new Message({
        value: "Message 2",
        topic: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bf0"),
        creator: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bef")
    }).save();

    console.log('Database initialized');
}

module.exports = {connect, disconnect}
"use strict"

const Topic = require("../models/topic.js").Topic;
const User = require("../models/user.js").User;
const Message = require("../models/message.js").Message;

const Mapper = require('./mapper.js');
const simpleTopicMapper = Mapper.simpleTopicMapper;
const topicMapper = Mapper.topicMapper;
const topicMessageMapper = Mapper.topicMessageMapper;
const useMessageMapper = Mapper.userMessageMapper;

const ObjectId = require('mongoose').Types.ObjectId;

async function findAllTopics(req, res, next) {
    const topics = await Topic
        .find();

    res.status(200);
    res.json(simpleTopicMapper(topics));
}


async function findTopicById(req, res, next) {
    const id = req.params.id;

    if (!ObjectId.isValid(id)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const topic = await Topic
        .findById(id)
        .populate("creator");

    if (topic == null) {
        res.status(404);
        res.json({message: `Book with id ${id} not found`});
        return;
    }

    const messages = await Message
        .find({topic: id})
        .populate("creator")
        .populate("topic");

    res.status(200);
    res.json({topic: topicMapper(topic), comments: topicMessageMapper(messages)});
}

async function saveTopic(req, res, next) {
    const title = req.body.title;
    const creator = req.body.creator;

    const user = await User.findOne({nick: creator});

    if (user == null) {
        res.status(404);
        res.json({message: `User with nick '${creator}' not found`});
        return;
    }

    await new Topic({
        title: title,
        creator: creator._id
    }).save();

    res.status(201);
    res.json();
}

async function deleteTopic(req, res, next) {
    const id = req.params.id;

    if (!ObjectId.isValid(id)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const topic = await Topic.findById(id);

    if (topic == null) {
        res.status(404);
        res.json({message: `Topic with id '${id}' not found`});
        return;
    }

    const messages = await Message
        .find({topic: id});
    messages.forEach(item => Message.findByIdAndDelete(item._id));

    topic.remove();

    res.status(204);
    res.json();
}

async function findAllTopicsByUser(req, res, next) {
    const userId = req.params.userId;

    if (!ObjectId.isValid(userId)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const user = await User.findById(userId);

    if (user == null) {
        res.status(404);
        res.json({message: `User with id '${userId}' not found`});
        return;
    }

    const topics = await Topic
        .find({creator: userId});

    res.status(200);
    res.json(simpleTopicMapper(topics));
}

async function addMessageInTopic(req, res, next) {
    const topicId = req.body.topicId;
    const messageValue = req.body.value;
    const creator = req.body.nick;

    if (!ObjectId.isValid(topicId)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const topic = await Topic
        .findById(topicId);

    if (topic == null) {
        res.status(404);
        res.json({message: `Topic with id ${topicId} not found`});
        return;
    }

    const user = await User.findOne({nick: creator});

    if (user == null) {
        res.status(404);
        res.json({message: `User with nick ${creator} not found`});
        return;
    }

    await new Message({
        value: messageValue,
        topic: topicId,
        creator: user._id
    }).save();

    res.status(201);
    res.json();
}

async function deleteMessage(req, res, next) {
    const messageId = req.params.messageId;

    if (!ObjectId.isValid(messageId)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    await Message.findByIdAndDelete(messageId);

    res.status(204);
    res.json();
}

async function findAllMessagesByUser(req, res, next) {
    const userId = req.params.userId;

    if (!ObjectId.isValid(userId)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const user = await User.findById(userId);

    if (user == null) {
        res.status(404);
        res.json({message: `User with id '${userId}' not found`});
        return;
    }

    const messages = await Message
        .find({creator: userId});

    res.status(200);
    res.json(useMessageMapper(messages));
}

module.exports = {
    findAllTopics,
    findTopicById,
    saveTopic,
    deleteTopic,
    findAllTopicsByUser,
    addMessageInTopic,
    deleteMessage,
    findAllMessagesByUser
}
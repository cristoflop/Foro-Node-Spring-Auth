"use strict"

const User = require("../models/user.js").User;
const Message = require("../models/message.js").Message;
const Topic = require("../models/topic.js").Topic;

const isValidEmail = require("../models/user.js").isValidEmail;

const Mapper = require('./mapper.js');
const userMapper = Mapper.userMapper;

const ObjectId = require('mongoose').Types.ObjectId;

async function findAllUsers(req, res, next) {
    const users = await User
        .find();

    res.status(200);
    res.json(userMapper(users));
}

async function findUserById(req, res, next) {
    const id = req.params.id;

    if (!ObjectId.isValid(id)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const user = await User.findById(id);
    if (user == null) {
        res.status(404);
        res.json({message: `User with id: ${id} not found`});
        return;
    }

    res.status(200);
    res.json(userMapper(user));
}

async function saveUser(req, res, next) {
    const nick = req.body.nick;
    const password = req.body.password;
    const email = req.body.email;

    let user = await User.findOne({nick: nick});

    if (user != null) {
        res.status(409);
        res.json({message: `User with nick '${nick}' already exists`});
        return;
    }

    if (!isValidEmail(email)) {
        res.status(400);
        res.json({message: `Email '${email}' is not valid`});
        return;
    }

    await new User({
        nick: nick,
        email: email,
        password: password,
        admin: false
    }).save();

    res.status(201);
    res.json();
}

async function updateUserEmail(req, res, next) {
    const email = req.body.email;
    const userId = req.body.userId;

    if (!ObjectId.isValid(id)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    if (!isValidEmail(email)) {
        res.status(400);
        res.json({message: `Email '${email}' is not valid`});
        return;
    }

    const user = await User.findByIdAndUpdate(userId, {email: email});

    if (user == null) {
        res.status(404);
        res.json({message: `User with id ${userId} not found`});
        return;
    }

    res.status(204);
    res.json();
}

async function deleteUser(req, res, next) {
    const id = req.params.id;

    if (!ObjectId.isValid(id)) {
        res.status(400);
        res.json({message: `Not valid id`});
        return;
    }

    const userTopics = await Topic.count({creator: id});
    const userMessages = await Message.count({creator: id});

    if (userTopics > 0 || userMessages > 0) {
        res.status(409);
        res.json({message: `The user has associated topics or messages`});
        return;
    }

    const user = await User.findByIdAndDelete(id);

    if (user == null) {
        res.status(404);
        res.json({message: `User with id ${id} not found`});
        return;
    }

    res.status(204);
    res.json();
}

module.exports = {findAllUsers, findUserById, saveUser, updateUserEmail, deleteUser}
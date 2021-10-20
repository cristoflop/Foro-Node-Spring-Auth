"use strict"

function simpleTopicMapper(topic) {
    if (topic instanceof Array)
        return topic.map(item => topicMapper(item));
    else
        return {id: topic._id, title: topic.title}
}

function topicMapper(topic) {
    if (topic instanceof Array)
        return topic.map(item => topicMapper(item));
    else
        return {id: topic._id, title: topic.title, creator: userMapper(topic.creator)}
}

function topicMessageMapper(message) {
    if (message instanceof Array)
        return message.map(item => topicMessageMapper(item));
    else
        return {id: message._id, value: message.value, creator: message.creator.nick}
}

function userMessageMapper(message) {
    if (message instanceof Array)
        return message.map(item => userMessageMapper(item));
    else
        return {id: message._id, value: message.value, topic: message.topic.title}
}

function userMapper(user) {
    if (user instanceof Array)
        return user.map(item => userMapper(item));
    else
        return {id: user._id, nick: user.nick, email: user.email}
}

module.exports = {userMapper, topicMapper, simpleTopicMapper, topicMessageMapper, userMessageMapper}
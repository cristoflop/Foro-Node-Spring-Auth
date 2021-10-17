"use strict"

function topicMapper(topic) {
    return {id: topic._id, title: topic.title, creator: topic.creator}
}

function messageMapper(message) {
    return {id: message._id, value: message.value, creator: message.creator.nick, topic: message.topic.title}
}

function userMapper(user) {
    return {id: user._id, nick: user.nick, email: user.email}
}

module.exports = {userMapper, topicMapper, messageMapper}
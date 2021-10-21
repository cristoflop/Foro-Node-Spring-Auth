"use strict"

const express = require('express');
const topicRouter = express.Router();
const topicController = require('../controller/topicController.js');

const {verify} = require('../auth/verifyAuth.js');

topicRouter.get("/topics", topicController.findAllTopics);

topicRouter.get("/topics/:id", verify, topicController.findTopicById);

topicRouter.post("/topics", verify, topicController.saveTopic);

topicRouter.delete("/topics/:id", verify, topicController.deleteTopic);

topicRouter.get("/topics/user/:userId", verify, topicController.findAllTopicsByUser);

topicRouter.post("/messages", verify, topicController.addMessageInTopic);

topicRouter.delete("/messages/:messageId", verify, topicController.deleteMessage);

topicRouter.get("/messages/user/:userId", verify, topicController.findAllMessagesByUser);

module.exports = topicRouter;
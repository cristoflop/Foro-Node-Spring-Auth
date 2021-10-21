"use strict"

const express = require('express');
const userRouter = express.Router();
const userController = require('../controller/userController.js');

const {verify, verifyAdminUser} = require('../auth/verifyAuth');

userRouter.get("/users", verify, verifyAdminUser, userController.findAllUsers);

userRouter.get("/users/:id", verify, verifyAdminUser, userController.findUserById);

userRouter.post("/users", userController.saveUser);

userRouter.patch("/users", verify, verifyAdminUser, userController.updateUserEmail);

userRouter.delete("/users/:id", verify, verifyAdminUser, userController.deleteUser);

module.exports = userRouter;
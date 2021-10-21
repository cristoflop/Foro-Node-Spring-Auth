"use strict"

const User = require('../models/user.js').User;

async function verify(req, res, next) {
    try {
        // verify auth credentials
        const base64Credentials = req.headers.authorization.split(' ')[1];
        const credentials = Buffer.from(base64Credentials, 'base64').toString('ascii');
        const [username, password] = credentials.split(':');

        if (username === undefined || password === undefined) {
            throw new Error();
        }

        if (req.user !== undefined) {
            if (req.user.nick === username && req.user.password === password) {
                next();
            }
        }

        const user = await User.findOne({
            nick: username,
            password: password
        });

        if (user == null) {
            throw new Error();
        }

        req.user = user;
        next();
    } catch (e) {
        res.status(401).send({
            error: 'Please authenticate.'
        });
    }
}

async function verifyAdminUser(req, res, next) {
    const user = req.user;

    if (user !== undefined && user.roles.some(item => item === "ROLE_ADMIN")) {
        next();
    } else {
        res.status(403).send({
            error: 'Need Permission.'
        });
    }
}

module.exports = {verify, verifyAdminUser};
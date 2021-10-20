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

        const user = await User.findOne({
            nick: username,
            password: password
        })

        if (user == null) {
            throw new Error();
        }

        req.user = user
        next()
    } catch (e) {
        res.status(401).send({
            error: 'Please authenticate.'
        })
    }
}

module.exports = {verify};
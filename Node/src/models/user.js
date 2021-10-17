const mongoose = require('mongoose');

const isValidEmail = function (email) {
    let mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    return typeof email == 'string' && email != "" && mailformat.test(email);
}

const userSchema = new mongoose.Schema({
    nick: {
        type: String,
        required: [true, 'Username is mandatory'],
        unique: true
    },
    password: {
        type: String,
        required: [true, 'Password is mandatory'],
        select: false
    },
    email: {
        type: String,
        validate: {
            validator: isValidEmail,
            message: props => `${props.value} is not a valid email`
        },
        required: [true, 'Email is mandatory']
    }
});

const User = mongoose.model('users', userSchema);

module.exports = {User, isValidEmail}

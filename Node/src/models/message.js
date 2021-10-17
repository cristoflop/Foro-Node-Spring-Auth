const mongoose = require('mongoose');

const messageSchema = new mongoose.Schema({
    value: {
        type: String,
        required: [true, 'Comment is mandatory']
    },
    topic: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'topics',
        required: [true, 'Topic is mandatory']
    },
    creator: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        required: [true, 'User is mandatory']
    }
});

const Message = mongoose.model('messages', messageSchema);

module.exports = {Message}

const mongoose = require('mongoose');

const topicSchema = new mongoose.Schema({
    title: {
        type: String,
        required: [true, 'Title is mandatory']
    },
    creator: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        required: [true, 'User is mandatory']
    }
});

const Topic = mongoose.model('topics', topicSchema);

module.exports = {Topic}

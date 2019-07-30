import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    contratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    tokenNotification: String,
    _created: {
      type: Date,
      default: Date.now
    }
  });

  return mongoose.model('notificacoes', Schema);
};

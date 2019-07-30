import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    bloqueante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    bloqueado: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    _created: {
      type: Date,
      default: Date.now
    }
  });
  return mongoose.model('bloqueados', Schema);
};

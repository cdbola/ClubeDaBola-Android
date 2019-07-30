import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    partida: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'partidas'
    },
    contratado: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    _created: {
      type: Date,
      default: Date.now
    }
  });
  return mongoose.model('selecionados', Schema);
};

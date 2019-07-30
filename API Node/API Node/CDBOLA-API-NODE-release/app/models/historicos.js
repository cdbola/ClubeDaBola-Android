import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    contratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    contratado: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    partida: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'partidas'
    },
    avaliacaoContratante: Number,
    avaliacaoContratado: Number,
    _created: {
      type: Date,
      default: Date.now
    }
  });

  return mongoose.model('historicos', Schema);
};

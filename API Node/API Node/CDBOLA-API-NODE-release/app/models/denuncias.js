import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    denunciante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    denunciado: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    descricao: String,
    status: String,
    _created: {
      type: Date,
      default: Date.now
    }
  });
  return mongoose.model('denuncias', Schema);
};

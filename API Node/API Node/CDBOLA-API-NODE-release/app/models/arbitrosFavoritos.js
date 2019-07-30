import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    contratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    arbitro: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'arbitros'
    },
    _created: {
      type: Date,
      default: Date.now
    }
  });
  return mongoose.model('arbitros_favoritos', Schema);
};

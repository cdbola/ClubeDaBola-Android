import mongoose from 'mongoose';
import bcrypt from 'bcrypt';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    nome: String,
    apelido: String,
    avatar: String,
    dataNascimento: String,
    email: String,
    customerId: String,
    senha: { type: String },
    arbitro: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'arbitros'
    },
    goleiro: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'goleiros'
    },
    _created: {
      type: Date,
      default: Date.now
    }
  });

  Schema.methods.generateHash = function(senha) {
    return bcrypt.hashSync(senha, bcrypt.genSaltSync(8), null);
  };
  Schema.methods.validPassword = function(senha, senhaSecreta) {
    return bcrypt.compareSync(senha, senhaSecreta);
  };

  return mongoose.model('contratantes', Schema);
};

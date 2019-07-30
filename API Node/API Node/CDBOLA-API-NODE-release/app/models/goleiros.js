import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    contratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    dadosBancarios: {
      cpf: String,
      banco: String,
      agencia: String,
      conta: String
    },
    genero: String,
    tamanhoLuva: String,
    tamanhoCamiseta: String,
    endereco: {
      lagradouro: String,
      numero: String,
      complemento: String,
      bairro: String,
      loc: {
        coordinates: [Number]
      }
    },
    telefone: String,
    celular: String,
    avatar: String,
    notificacoes: Boolean,
    raio: Number,
    _created: {
      type: Date,
      default: Date.now
    }
  });

  return mongoose.model('goleiros', Schema);
};

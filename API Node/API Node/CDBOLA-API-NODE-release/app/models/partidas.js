import mongoose from 'mongoose';
mongoose.Promise = global.Promise;

module.exports = () => {
  const Schema = new mongoose.Schema({
    tipoJogo: String,
    genero: String,
    data: String,
    endereco: {
      lagradouro: String,
      numero: String,
      complemento: String,
      bairro: String,
      loc: {
        coordinates: [Number]
      }
    },
    notaContratado: Number,
    notaContratante: Number,
    preferenciaContratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    comeca: String,
    termina: String,
    observacoes: String,
    tipoPessoa: String,
    contratante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    contratado: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'contratantes'
    },
    aleatorio: Boolean,
    valor: Number,
    _created: {
      type: Date,
      default: Date.now
    }
  }, {
    toObject: {
      virtuals: true
    },
    toJSON: {
      virtuals: true
    }
  });

  Schema.virtual('tempo').get(function () {
    const tempo = this.termina.split(':').join('') - this.comeca.split(':').join('');
    let hours = Math.floor(tempo / 90);
    let minutes = tempo % 60;
    return `${hours}:${minutes}`;
  });

  return mongoose.model('partidas', Schema);
};

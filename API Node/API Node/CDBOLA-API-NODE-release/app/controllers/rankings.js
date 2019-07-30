import { Listar, Obter, Deletar, Salvar } from '../helpers'
const ObjectId = require('mongoose').Types.ObjectId;

module.exports = (app) => {
  const controller = {};
  const GoleirosModel = app.models.goleiros;
  const ArbitrosModel = app.models.arbitros;
  const PartidasModel = app.models.partidas;
  const configRetorno = {
    __v: 0
  }

  const listarGoleiros = ({req, res, callback}) => {
    const rankingGoleiros = [];
    let countGoleiros = null;

    GoleirosModel.find({}).populate('contratante').exec()
      .then((goleiros) => {
        if (goleiros.length === 0) {
          if (!callback)
            res.status(200).json(rankingGoleiros);
          else
            return callback(rankingGoleiros);
        }

        if (countGoleiros == null)
          countGoleiros = goleiros.length;

        const eachGoleiro = new Promise((resolve, reject) => {

          goleiros.forEach((goleiro) => {
            let soma = 0;
            
            if (goleiro.contratante) {
              PartidasModel.find({contratado: new ObjectId(goleiro.contratante._id)}).exec().then((partidas) => {

                partidas.forEach((partida) => {
                  if (partida.notaContratado){
                    soma += partida.notaContratado;
                  } else{
                    soma += 10;
                  } 


                  
                });

                if (partidas.length > 0) {
                  rankingGoleiros.push({
                    nome: goleiro.contratante.nome,
                    avatar: goleiro.contratante.avatar,
                    media: Number((soma / partidas.length).toFixed(2)),
                    partidas: partidas.length,
                    endereco: goleiro.endereco
                  });
                }

                countGoleiros--;

                if (countGoleiros == 0) {
                  resolve(rankingGoleiros);
                  countGoleiros = null;
                }
              });
            } else {
              countGoleiros--;
            }

          });
        });

        Promise.all([eachGoleiro]).then((rankGoleiros) => {
          if (!callback) {
            Array.isArray(rankGoleiros[0]) ? res.status(200).json(rankGoleiros[0]) : res.status(200).json(rankGoleiros);
          }
          else {
            Array.isArray(rankGoleiros[0]) ? callback(rankGoleiros[0]) : callback(rankGoleiros)
          }
        });
      }).catch((err) => {
        console.log(err)
      });
  };

  const listarArbitros = ({req, res, callback}) => {
    const rankingArbitros = [];
    let countArbitros = null;

    ArbitrosModel.find({}).populate('contratante').exec()
      .then((arbitros) => {
        if (arbitros.length === 0) {
          if (!callback)
            res.status(200).json(rankingArbitros);
          else
            return callback(rankingArbitros);
        }

        if (countArbitros == null)
          countArbitros = arbitros.length;

        const eachArbitros = new Promise((resolve, reject) => {

          arbitros.forEach((arbitro) => {
            let soma = 0;

            if (arbitro.contratante) {
              PartidasModel.find({contratado: new ObjectId(arbitro.contratante._id)}).exec().then((partidas) => {

                partidas.forEach((partida) => {
                  if (partida.notaContratado){
                    soma += partida.notaContratado;
                  } else{
                    soma += 10;
                  } 
                });

                if (partidas.length > 0) {
                  rankingArbitros.push({
                    nome: arbitro.contratante.nome,
                    avatar: arbitro.contratante.avatar,
                    media: soma / partidas.length,
                    partidas: partidas.length,
                    endereco: arbitro.endereco
                  });
                }

                countArbitros--;

                if (countArbitros == 0) {
                  resolve(rankingArbitros);
                  countArbitros = null;
                }
              });
            } else {
              countArbitros--;
            }

          });
        });

        Promise.all([eachArbitros]).then((rankArbitros) => {
          if (!callback) {
            Array.isArray(rankArbitros[0]) ? res.status(200).json(rankArbitros[0]) : res.status(200).json(rankArbitros)
          }
          else {
            Array.isArray(rankArbitros[0]) ? callback(rankArbitros[0]) : callback(rankArbitros);
          }
        });
      }).catch((err) => {
        console.log(err)
      });
  };

  controller.listarGoleiros = (req, res) => {
    return listarGoleiros({req, res})
  };

  controller.listarArbitros = (req, res) => {
    return listarArbitros({req, res});
  };

  controller.listarTodos = async (req, res) => {
    listarArbitros({callback: (arbitros) => {

      listarGoleiros({callback: (goleiros) => {
        res.status(200).json({
          arbitros,
          goleiros
        });
      }});

    }});
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'
import async from 'async';

module.exports = (app) => {
  const controller = {};
  const moip = app.get('moip');
  const PartidasModel = app.models.partidas;
  const Carteiras = app.controllers.carteiras;
  const Notificacoes = app.controllers.notificacoes;
  const Selecionados = app.controllers.osSelecionados;
  const SelecionadosModel = app.models.osSelecionados
  const GoleirosModel = app.models.goleiros;
  const ArbitrosModel = app.models.arbitros;
  const configRetorno = {
    __v: 0
  }
  const moment = app.get('moment');
  controller.listarPartidas = (req, res) => {
    Listar({
      model: PartidasModel,
      config: configRetorno,
      populate: ['contratante', 'contratado'],
        success: (partidas) => {
        res.status(200).json(partidas);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar as partidas'
        });
      }
    });
  };

  controller.obterPartida = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: PartidasModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante', 'contratado'],
      success: (partida) => {
        res.status(200).json(partida);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter a partida!'
        });
      }
    });
  };

  controller.deletarPartida = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: PartidasModel,
      _id: _id,
      success: (partida) => {
        res.status(200).json({
          partida: partida,
          sucesso: true,
          mensagem: 'Partida deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar esta partida!'
        });
      }
    });
  };

  let message = {
    "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
    "notification":{
      "title":"Você tem uma nova partida",
      "body":"Você foi selecionado para um partida perto de você"
    },
    "data" : {
      "Nick" : "Mario",
      "Room" : "PortugalVSDenmark"
    }
  };

  controller.buscaPorRaio = (Model, coordinates, callback) => {
    let _jogadores = [];
    let contagem = 0;
    let countJogadores = 0;
    Model.find({}).exec().then((jogadores) => {
      countJogadores = jogadores.length;

      async.each(jogadores, function(jogador, done) {
        let lat1 = jogador.endereco.loc.coordinates[0];
        let lat2 = coordinates[0];
        let lon1 = jogador.endereco.loc.coordinates[1];
        let lon2 = coordinates[1];
        let unit = 'K';

        if ((lat1 == lat2) && (lon1 == lon2)) {
          return 0;
        }
        else {
          var radlat1 = Math.PI * lat1/180;
          var radlat2 = Math.PI * lat2/180;
          var theta = lon1-lon2;
          var radtheta = Math.PI * theta/180;
          var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
          if (dist > 1) {
            dist = 1;
          }
          dist = Math.acos(dist);
          dist = dist * 180/Math.PI;
          dist = dist * 60 * 1.1515;
          if (unit=="K") { dist = dist * 1.609344 }
          if (unit=="N") { dist = dist * 0.8684 }

          contagem++;
          if (dist <= jogador.raio) {
            _jogadores.push(jogador);
          }

          if (contagem == countJogadores) {
            done(_jogadores)
          }
        }
      }, function(jogadores) {
        callback(jogadores);
      });
    });
  };

  controller.obterToken = (_id, callback) => {
    Notificacoes.buscarNotificacao(_id, (notificacao) => {
      if (notificacao)
        callback(notificacao.tokenNotification);
      else
        callback(notificacao);
    });
  };

  controller.enviaPush = (partida) => {
    if (partida.preferenciaContratante) {
      // obter notificacao token
      controller.obterToken(partida.preferenciaContratante._id, (token) => {
        if (token) {
          message.token = token;
          Selecionados.salvarSelecionado({
            partida: partida._id,
            contratado: partida.preferenciaContratante,
          }).then((selecionado) => {
            app.get('firebase').messaging().send(message)
              .then((response) => {
                console.log('Successfully sent message:', response);
              })
              .catch((error) => {
                console.log('Error sending message:', error);
              });
          }).catch((e) => {
            res.status(400).json({
              error: e,
              message: 'Erro ao salvar o selecionado'
            })
          });
        } else {
          console.log('nao existe token')
        }
      });

      controller.buscaPorRaio(partida.tipoPessoa == 'G' ? GoleirosModel : ArbitrosModel, partida.endereco.loc.coordinates, (jogadores) => {
        if (jogadores.length > 0) {
          jogadores.forEach((jogador) => {
            controller.obterToken(jogador.contratante.toString(), (token) => {
              if (token) {
                message.token = token;
                Selecionados.salvarSelecionado({
                  partida: partida._id,
                  contratado: jogador.contratante._id,
                }).then((selecionado) => {
                  app.get('firebase').messaging().send(message)
                    .then((response) => {
                      console.log('Successfully sent message:', response);
                    })
                    .catch((error) => {
                      console.log('Error sending message:', error);
                    });
                }).catch((e) => {
                  res.status(400).json({
                    error: e,
                    message: 'Erro ao salvar o selecionado'
                  })
                });
              } else {
                console.log('Jogador não existe token para push');
              }
            });
          });
        } else {
          console.log('Jogadores não encontrados no RAIO');
        }
      });


    } else {
      // PROCURAR POR RAIO
      controller.buscaPorRaio(partida.tipoPessoa == 'G' ? GoleirosModel : ArbitrosModel, partida.endereco.loc.coordinates, (jogadores) => {
        if (jogadores.length > 0) {
          jogadores.forEach((jogador) => {
            controller.obterToken(jogador.contratante.toString(), (token) => {
              if (token) {
                message.token = token;
                console.log(`Jogador id ${jogador.contratante._id}`)
                Selecionados.salvarSelecionado({
                  partida: partida._id,
                  contratado: jogador.contratante._id,
                }).then((selecionado) => {
                  app.get('firebase').messaging().send(message)
                    .then((response) => {
                      console.log('Successfully sent message:', response);
                    })
                    .catch((error) => {
                      console.log('Error sending message:', error);
                    });
                }).catch((e) => {
                  res.status(400).json({
                    error: e,
                    message: 'Erro ao salvar o selecionado'
                  })
                });
              } else {
                console.log('Jogador não existe token para push');
              }
            });
          });
        } else {
          console.log('Jogadores não encontrados no RAIO');
        }
      });
    }
  };

  controller.salvarPartida = (req, res) => {
    const _id = req.body._id;
    const customerId = req.body.customerId;
    const contratadoId = req.body.contratado;
    const cvc = req.body.cvc;
    console.log(`Numero do CVC: ${cvc}`);
    Salvar({
      model: PartidasModel,
      _id: _id,
      data: req.body,
      success: (partida) => {
        moip.customer.getOne(customerId)
        .then((resCustomer) => {
          const creditCardId = resCustomer.body.fundingInstrument.creditCard.id;

          let order = {
            ownId: partida.id,
            amount: {
              currency: 'BRL',
              subtotals: {
                shipping: 0
              }
            },
            items: [{
              product: 'Contratação de um Jogador',
              quantity: 1,
              detail: `${partida.comeca}-${partida.termina}`,
              price: partida.valor
            }],
            customer: {
              id: customerId
            }
          };

          moip.order.create(order).then((resOrder) => {

            moip.payment.create(resOrder.body.id, {
              installmentCount: 1,
              fundingInstrument: {
                method: 'CREDIT_CARD',
                creditCard: {
                  cvc: req.body.cvc,
                  id: creditCardId
                }
              }
            }).then((resPayment) => {

              controller.enviaPush(partida);

              res.status(201).json({
                payment: resPayment.body,
                order: resOrder.body,
                partida: partida,
                sucesso: true,
                mensagem: 'partida atualizado com sucesso!'
              });
            }).catch((err) => {
               console.log(err)
            });

          }).catch((err) => {
            console.log(err);
          })

        })
        .catch((err) => {
          res.status(400).json({
            sucesso: false,
            erro: err,
            mensagem: 'Não foi possível obter o customer'
          });
        })
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar esta partida'
        });
      }
    });
  };

  controller.listarPartidasRecentes = (req, res) => {
    const contratanteId = req.params.contratanteId;

    PartidasModel.find({contratante: contratanteId}).exec().then((partidas) => {

      let resultadoPartidas = [];
      partidas.filter((partida, index) => {
        let hoje = moment().format('DD-MM-YYYY');
        let hora = moment().format('HH:mm');
        
        console.log(hoje);
        console.log(hora);
        console.log(partida.data);
        if(moment(moment(hoje, 'DD-MM-YYYY')).isBefore(moment(partida.data,'DD-MM-YYYY'))) {
          console.log('NOIS');
          resultadoPartidas.push(partida);
        }
        else if (hoje == partida.data) {
          if (moment(moment(hora, 'HH:mm')).isBefore(moment(partida.termina, 'HH:mm'))) {
            resultadoPartidas.push(partida);
          }
        }
      });

      res.status(200).json(resultadoPartidas);
    });
  };

  controller.listarPartidasRecentesContratado = (req, res) => {
    const contratanteId = req.params.contratanteId;

    PartidasModel.find({contratado: contratanteId}).exec().then((partidas) => {
      console.log(JSON.stringify(partidas, null, 4));


      let resultadoPartidas = [];
      partidas.filter((partida, index) => {
        let hoje = moment().format('DD-MM-YYYY');
        let hora = moment().format('HH:mm');

        if(moment(moment(hoje, 'DD-MM-YYYY')).isBefore(moment(partida.data,'DD-MM-YYYY'))) {
          resultadoPartidas.push(partida);
        }
        else if (hoje == partida.data) {
          if (moment(moment(hora, 'HH:mm')).isBefore(moment(partida.termina, 'HH:mm'))) {
            resultadoPartidas.push(partida);
          }
        }
      });




      SelecionadosModel.find({contratado:contratanteId}).populate(['contratado', 'partida']).exec().then((selecionados) => {
        let resultadoPendente = [];
        
        console.log(`tamanho do array ${selecionados.length}`);
        
        console.log(JSON.stringify(selecionados, null, 4));

        selecionados.filter((selecionado, index) => {
        
          console.log(`Dentro do forEach`);
          console.log(`${selecionado.partida}`);
          
            let hoje = moment().format('DD-MM-YYYY');
            let hora = moment().format('HH:mm');

            if(selecionado.partida.contratado){

            } else{
              if(moment(moment(hoje, 'DD-MM-YYYY')).isBefore(moment(selecionado.partida.data,'DD-MM-YYYY'))) {
                console.log(`Dentro if`);
                resultadoPartidas.push(selecionado.partida);
                resultadoPendente.push(selecionado.partida);
             }
              else if (hoje == selecionado.partida.data) {

                if (moment(moment(hora, 'HH:mm')).isBefore(moment(selecionado.partida.termina, 'HH:mm'))) {
                     console.log(`Dentro if2`);
                     resultadoPartidas.push(selecionado.partida);
                     resultadoPendente.push(selecionado.partida);
                 }
              }
            }

        });
          console.log(`Fora do forEach`);
          res.status(200).json({
                partidas: resultadoPartidas,
                sucesso: true
          });

      });
    });
  };



  controller.listarPartidasPassadas = (req, res) => {
    const contratanteId = req.params.contratanteId;

    PartidasModel.find({contratante: contratanteId}).populate(['contratado','contratante']).exec().then((partidas) => {

      let resultadoPartidas = [];
      partidas.filter((partida, index) => {
        moment.locale('pt-br');
        let hoje = moment().format('DD-MM-YYYY');
        let hora = moment().format('HH:mm');
        

        console.log(hoje);
        console.log(hora);
        console.log(partida.data);
        if(moment(moment(hoje, 'DD-MM-YYYY')).isAfter(moment(partida.data,'DD-MM-YYYY'))) {
          console.log('Dentro IF');
          resultadoPartidas.push(partida);
        }
        else if (hoje == partida.data) {
          if (moment(moment(hora, 'HH:mm')).isAfter(moment(partida.termina, 'HH:mm'))) {
            resultadoPartidas.push(partida);
          }
        }
      });

      res.status(200).json(resultadoPartidas);
    });
  };

  controller.listarPartidasPassadasContratado = (req, res) => {
    const contratanteId = req.params.contratanteId;

    PartidasModel.find({contratado: contratanteId}).populate(['contratado','contratante']).exec().then((partidas) => {

      let resultadoPartidas = [];
      partidas.filter((partida, index) => {
        let hoje = moment().format('DD-MM-YYYY');
        let hora = moment().format('HH:mm');

        if(moment(moment(hoje, 'DD-MM-YYYY')).isAfter(moment(partida.data,'DD-MM-YYYY'))) {
          resultadoPartidas.push(partida);
        }
        else if (hoje == partida.data) {
          if (moment(moment(hora, 'HH:mm')).isAfter(moment(partida.termina, 'HH:mm'))) {
            resultadoPartidas.push(partida);
          }
        }
      });

      res.status(200).json(resultadoPartidas);
    });
  };


  controller.vincular = (req, res) => {
    const partidaId = req.body.partida;

    PartidasModel.findOne({_id: partidaId}).exec().then((partida) => {
      
      if(partida.contratado){
          res.status(400).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível vincular esta partida'
         });
      }
      partida.contratado = req.body.contratante;
      partida.save();

      Carteiras.salvarPosPartida({
          body: {
                dataAdicao: partida.data,
                valor: partida.valor * 60 / 100, // 60% do valor da partida para o contratado
                idContratado: req.body.contratante
            }
      });

      res.status(201).json({
        partida: partida,
        sucesso: true,
        mensagem: 'partida vinculado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível vincular esta partida'
      });
    });
  };

  controller.desvincular = (req, res) => {
    const partidaId = req.params.id;

    PartidasModel.findOne({_id: partidaId}).exec().then((partida) => {
      partida.contratado = undefined;
      partida.save();
      res.status(201).json({
        partida: partida,
        sucesso: true,
        mensagem: 'partida desvinculado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível desvincular esta partida'
      });
    });
  };

  controller.avaliar = (req, res) => {
    const partidaId = req.body.partida;
    const notaContratante = req.body.notaContratante;
    const notaContratado = req.body.notaContratado;

    PartidasModel.findOne({_id: partidaId}).exec().then((partida) => {
      partida.notaContratado = req.body.notaContratado;
      partida.notaContratante = req.body.notaContratante;
      partida.save();
      res.status(201).json({
        partida: partida,
        sucesso: true,
        mensagem: 'partida avaliada com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível avaliar esta partida'
      });
    });
  };

  return controller;
};

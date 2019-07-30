import { Listar, Obter, Deletar, Salvar } from '../helpers'
const ObjectId = require('mongoose').Types.ObjectId;
import async from 'async';
import hbs from 'nodemailer-express-handlebars';
import nodemailer from 'nodemailer';
import { config } from '../../config';

module.exports = (app) => {
  const controller = {};
  const CarteirasModel = app.models.carteiras;
  const configRetorno = {
    __v: 0
  }
  const moment = app.get('moment');

  const smtpTransport = nodemailer.createTransport({
    service: config.recuperar.provedor,
    host: config.recuperar.host,
    auth: {
      user: config.recuperar.email,
      pass: config.recuperar.senha
    }
  });

  const handlebarsOptions = {
    viewEngine: 'handlebars',
    viewPath: './app/templates/',
    extName: '.html'
  };

  smtpTransport.use('compile', hbs(handlebarsOptions));

  controller.listarCarteiras = (req, res) => {
    Listar({
      model: CarteirasModel,
      config: configRetorno,
      populate: ['contratante'],
      success: (carteiras) => {
        res.status(200).json(carteiras);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar as carteiras'
        });
      }
    });
  };

  controller.obterCarteira = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: CarteirasModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante'],
      success: (carteira) => {
        res.status(200).json(carteira);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter a carteira!'
        });
      }
    });
  };

  controller.deletarCarteira = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: CarteirasModel,
      _id: _id,
      success: (carteira) => {
        res.status(200).json({
          carteira: carteira,
          sucesso: true,
          mensagem: 'carteira deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar esta carteira!'
        });
      }
    });
  };

  controller.salvarCarteira = (req, res) => {
    const _id = req.body._id;

    Salvar({
      model: CarteirasModel,
      _id: _id,
      data: req.body,
      success: (carteira) => {
        res.status(201).json({
          carteira: carteira,
          sucesso: true,
          mensagem: 'carteira atualizado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar esta carteira'
        });
      }
    });
  };

  controller.salvarPosPartida = (req, res) => {
    const posPartida = req.body;

    CarteirasModel.findOne({contratante: new ObjectId(req.body.idContratado)}).exec().then((carteira) => {
      carteira.posPartida.push(posPartida);
      carteira.save();
    });
  };


  controller.getCarteira = (req, res) => {
    const contratante = req.params.id;
    
    CarteirasModel.findOne({contratante}).populate('contratante').exec().then((carteira) => {
        let _posPartidas = [];
        let contagem = 0;
        let countPosPartidas = carteira.posPartida.length;
        
        let pendingValue = 0;
        let totalValue = 0;
        let avaiableValue = 0;

        if (countPosPartidas > 0) {
          async.each(carteira.posPartida, (postPartida, done) => {
            _posPartidas.push(postPartida);
            totalValue += postPartida.valor;
            
            if (!postPartida.retirado) {
              let date = postPartida.dataAdicao.split('-').reverse().join('/');
              let diffDays = moment().diff(moment(date).format(), 'days');

            if (diffDays > 30) {
              avaiableValue += postPartida.valor;
            } else{
              pendingValue += postPartida.valor;
            }
          
          }

            contagem++;
            if (contagem == countPosPartidas) {
              done(_posPartidas);
            }
            
          },(posPartidas) => {
            res.status(200).json({
                sucesso: true,
                valorPendente:pendingValue,
                valorTotal:totalValue,
                valorDisponivel:avaiableValue,
                extrato:posPartidas,
                mensagem: 'Carteira obtida com sucesso'
            });
          });
        }else{
          res.status(200).json({
             sucesso: true,
             valorPendente:pendingValue,
             valorTotal:totalValue,
             valorDisponivel:avaiableValue,
             extrato:_posPartidas,
             mensagem: 'Carteira obtida com sucesso'
          });
        }
    });
  };


  controller.resgatarCarteira = (req, res) => {
    const contratante = req.body.contratante;

    CarteirasModel.findOne({contratante}).populate('contratante').exec().then((carteira) => {
      
      let _posPartidas = [];
      let contagem = 0;
      let countPosPartidas = carteira.posPartida.length;
      console.log(JSON.stringify(carteira, null, 4));


      if (countPosPartidas > 0) {
        async.each(carteira.posPartida, (postPartida, done) => {
          if (!postPartida.retirado) {
            let date = postPartida.dataAdicao.split('-').reverse().join('/');
            let diffDays = moment().diff(moment(date).format(), 'days');

            if (diffDays > 30) {
              postPartida.retirado = true;
              carteira.save();
              _posPartidas.push(postPartida);
            }
          }

          contagem++;
          if (contagem == countPosPartidas) {
            done(_posPartidas);
          }
        }, (posPartidas) => {
          if (posPartidas.length > 0) {
            const data = {
              to: 'pagamentos@cdbola.com.br',
              from: config.recuperar.email,
              template: 'resgatar-pospartidas',
              subject: 'Resgate de carteiras pós partidas',
              context: {
                name: carteira.contratante.nome,
                posPartidas:posPartidas,
                contratante:contratante
              }
            };

            smtpTransport.sendMail(data, function(err) {
              if (!err) {
                return res.status(200).json({
                  success: true,
                  resgates: posPartidas,
                  message: 'Resgate enviado com sucesso'
                });
              } else {
                return res.status(400).json({
                  success: false,
                  message: 'Não foi possivel enviar o email'
                });
              }
            });
          } else {
            res.status(200).json({
              success: true,
              message: 'Não tem resgate para este contratante'
            });
          }
        });
      }else {
        res.status(200).json({
          success: true,
          message: 'Não existem carteiras para retirada'
        });
      }
    });
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const NotificacoesModel = app.models.notificacoes;
  const configRetorno = {
    __v: 0
  }

  controller.listarNotificacoes = (req, res) => {
    Listar({
      model: NotificacoesModel,
      config: configRetorno,
      populate: ['contratante'],
      success: (notificacoes) => {
        res.status(200).json(notificacoes);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar as notificacoes'
        });
      }
    });
  };

  controller.obterNotificacao = (req, res) => {
    const _id = req.params.id;

    NotificacoesModel.findOne({contratante: _id}).populate('contratante').exec().then((notificacao) => {
      res.status(200).json(notificacao);
    }).catch((err) => {
      res.status(404).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível obter a notificacao!'
      });
    });
  };

  controller.deletarNotificacao = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: NotificacoesModel,
      _id: _id,
      success: (notificacao) => {
        res.status(200).json({
          notificacao: notificacao,
          sucesso: true,
          mensagem: 'notificacao deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar esta notificacao!'
        });
      }
    });
  };

  controller.salvarNotificacao = (req, res) => {
    const _id = req.body._id;
    console.log('OI');
    console.log(req.body.contratante);

    NotificacoesModel.findOne({contratante: req.body.contratante}).exec().then((notificacao) => {
      if (notificacao){
        console.log('Aqui');
        notificacao.tokenNotification = req.body.tokenNotification;
        notificacao.save();
        res.status(201).json({
          sucesso: true,
          mensagem: 'notificacao atualizado com sucesso!'
        });
      }
      else{
       console.log('Aqui2');
        Salvar({
      model: NotificacoesModel,
      _id: _id,
      data: req.body,
      success: (notificacao) => {
        res.status(201).json({
          notificacao: notificacao,
          sucesso: true,
          mensagem: 'notificacao atualizado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar esta notificacao'
        });
      }
    });
      }

    });



    
  };

  controller.buscarNotificacao = (_id, callback) => {
    NotificacoesModel.findOne({contratante: _id}).exec().then((notificacao) => {
      callback(notificacao);
    });
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const HistoricosModel = app.models.historicos;
  const configRetorno = {
    __v: 0
  }

  controller.listarHistoricos = (req, res) => {
    Listar({
      model: HistoricosModel,
      config: configRetorno,
      populate: ['contratante', 'contratado', 'partida'],
      success: (historicos) => {
        res.status(200).json(historicos);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os historicos'
        });
      }
    });
  };

  controller.obterHistorico = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: HistoricosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante', 'contratado', 'partida'],
      success: (historico) => {
        res.status(200).json(historico);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o historico!'
        });
      }
    });
  };

  controller.deletarHistorico = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: HistoricosModel,
      _id: _id,
      success: (historico) => {
        res.status(200).json({
          historico: historico,
          sucesso: true,
          mensagem: 'Historico deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este historico!'
        });
      }
    });
  };

  controller.salvarHistorico = (req, res) => {
    const _id = req.body._id;

    Salvar({
      model: HistoricosModel,
      _id: _id,
      data: req.body,
      success: (historico) => {
        res.status(201).json({
          historico: historico,
          sucesso: true,
          mensagem: 'historico atualizado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este historico'
        });
      }
    });
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const SelecionadosModel = app.models.osSelecionados;
  const controller = {};
  const configRetorno = {
    __v: 0
  }

  controller.listarSelecionados = (req, res) => {
    Listar({
      model: SelecionadosModel,
      config: configRetorno,
      populate: ['contratado', 'partida'],
        success: (selecionados) => {
        res.status(200).json(selecionados);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os selecionados'
        });
      }
    });
  };

  controller.obterSelecionado = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: SelecionadosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratado', 'partida'],
      success: (selecionado) => {
        res.status(200).json(selecionado);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o selecionados!'
        });
      }
    });
  };

  controller.deletarSelecionado = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: SelecionadosModel,
      _id: _id,
      success: (selecionado) => {
        res.status(200).json({
          selecionado: selecionado,
          sucesso: true,
          mensagem: 'selecionado deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar esta selecionado!'
        });
      }
    });
  };

  controller.salvarSelecionado = (body) => {
    return SelecionadosModel.create(body);
  };

  return controller;
};

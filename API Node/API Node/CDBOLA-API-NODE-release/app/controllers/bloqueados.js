import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const BloqueadosModel = app.models.bloqueados;
  const configRetorno = {
    __v: 0
  }

  controller.listarBloqueados = (req, res) => {
    Listar({
      model: BloqueadosModel,
      config: configRetorno,
      populate: ['bloqueado', 'bloqueante'],
      success: (bloqueados) => {
        res.status(200).json(bloqueados);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os bloqueados'
        });
      }
    });
  };

  controller.obterBloqueado = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: BloqueadosModel,
      _id: _id,
      config: configRetorno,
      populate: ['bloqueado', 'bloqueante'],
      success: (bloqueado) => {
        res.status(200).json(bloqueado);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o bloqueado!'
        });
      }
    });
  };

  controller.deletarBloqueado = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: BloqueadosModel,
      _id: _id,
      success: (bloqueado) => {
        res.status(200).json({
          bloqueado: bloqueado,
          sucesso: true,
          mensagem: 'bloqueado deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este bloqueado!'
        });
      }
    });
  };

  controller.salvarBloqueado = (req, res) => {
    const _id = req.body._id;

    if(_id) {
      BloqueadosModel.findByIdAndUpdate(_id, req.body).exec().then(bloqueado => {
        res.status(201).json({
          bloqueado: bloqueado,
          sucesso: true,
          mensagem: 'Bloqueado favorito atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este bloqueado'
        });
      });
    }else {
      BloqueadosModel.create(req.body).then(bloqueado => {
        res.status(201).json({
          bloqueado: bloqueado,
          sucesso: true,
          mensagem: 'bloqueado salvo com sucesso!'
        });
      }).catch((err) => {
        res.status(400).json({
          goleiro: null,
          sucesso: false,
          mensagem: 'Nao foi possivel salvar',
        });
      });
    }
  };

  return controller;
};

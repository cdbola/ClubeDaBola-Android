import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const ArbitrosFavoritosModel = app.models.arbitrosFavoritos;
  const configRetorno = {
    __v: 0
  }

  controller.listarArbitrosFavoritos = (req, res) => {
    Listar({
      model: ArbitrosFavoritosModel,
      config: configRetorno,
      populate: ['contratante', 'arbitro'],
      success: (arbitrosFavoritos) => {
        res.status(200).json(arbitrosFavoritos);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os arbitros favoritos'
        });
      }
    });
  };

  controller.obterArbitroFavorito = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: ArbitrosFavoritosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante', 'arbitro'],
      success: (arbitroFavorito) => {
        res.status(200).json(arbitroFavorito);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o arbitro favorito!'
        });
      }
    });
  };

  controller.deletarArbitroFavorito = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: ArbitrosFavoritosModel,
      _id: _id,
      success: (arbitroFavorito) => {
        res.status(200).json({
          arbitroFavorito: arbitroFavorito,
          sucesso: true,
          mensagem: 'arbitro favorito deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este arbitro favorito!'
        });
      }
    });
  };

  controller.salvarArbitroFavorito = (req, res) => {
    const _id = req.body._id;

    if(_id) {
      ArbitrosFavoritosModel.findByIdAndUpdate(_id, req.body).exec().then(arbitroFavorito => {
        res.status(201).json({
          arbitroFavorito: arbitroFavorito,
          sucesso: true,
          mensagem: 'Arbitro favorito atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este arbitro favorito'
        });
      });
    }else {
      ArbitrosFavoritosModel.create(req.body).then(arbitroFavorito => {
        res.status(201).json({
          arbitroFavorito: arbitroFavorito,
          sucesso: true,
          mensagem: 'arbitro favorito salvo com sucesso!'
        });
      }).catch((err) => {
        res.status(400).json({
          arbitro: null,
          sucesso: false,
          mensagem: 'Nao foi possivel salvar o arbitro favorito',
        });
      });
    }
  };

  return controller;
};

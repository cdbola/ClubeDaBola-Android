import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const GoleirosFavoritosModel = app.models.goleirosFavoritos;
  const configRetorno = {
    __v: 0
  }

  controller.listarGoleirosFavoritos = (req, res) => {
    Listar({
      model: GoleirosFavoritosModel,
      config: configRetorno,
      populate: ['contratante', 'goleiro'],
      success: (goleirosFavoritos) => {
        res.status(200).json(goleirosFavoritos);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os goleiros favoritos'
        });
      }
    });
  };

  controller.obterGoleiroFavorito = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: GoleirosFavoritosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante', 'goleiro'],
      success: (goleiroFavorito) => {
        res.status(200).json(goleiroFavorito);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o goleiro favorito!'
        });
      }
    });
  };

  controller.deletarGoleiroFavorito = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: GoleirosFavoritosModel,
      _id: _id,
      success: (goleiroFavorito) => {
        res.status(200).json({
          goleiroFavorito: goleiroFavorito,
          sucesso: true,
          mensagem: 'goleiro favorito deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este goleiro favorito!'
        });
      }
    });
  };

  controller.salvarGoleiroFavorito = (req, res) => {
    const _id = req.body._id;

    if(_id) {
      GoleirosFavoritosModel.findByIdAndUpdate(_id, req.body).exec().then(goleiroFavorito => {
        res.status(201).json({
          goleiroFavorito: goleiroFavorito,
          sucesso: true,
          mensagem: 'Goleiro favorito atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este goleiro favorito'
        });
      });
    }else {
      GoleirosFavoritosModel.create(req.body).then(goleiroFavorito => {
        res.status(201).json({
          goleiroFavorito: goleiroFavorito,
          sucesso: true,
          mensagem: 'goleiro favorito salvo com sucesso!'
        });
      }).catch((err) => {
        res.status(400).json({
          goleiro: null,
          sucesso: false,
          mensagem: 'Nao foi possivel salvar o goleiro favorito',
        });
      });
    }
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const DenunciasModel = app.models.denuncias;
  const configRetorno = {
    __v: 0
  }

  controller.listarDenuncias = (req, res) => {
    Listar({
      model: DenunciasModel,
      config: configRetorno,
      populate: ['denunciante', 'denunciado'],
      success: (denuncias) => {
        res.status(200).json(denuncias);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar as denuncias'
        });
      }
    });
  };

  controller.obterDenuncia = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: DenunciasModel,
      _id: _id,
      populate: ['denunciante', 'denunciado'],
      config: configRetorno,
      success: (denuncia) => {
        res.status(200).json(denuncia);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter a denuncia!'
        });
      }
    });
  };

  controller.deletarDenuncia = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: DenunciasModel,
      _id: _id,
      success: (denuncia) => {
        res.status(200).json({
          denuncia: denuncia,
          sucesso: true,
          mensagem: 'denuncia deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar esta denuncia!'
        });
      }
    });
  };

  controller.salvarDenuncia = (req, res) => {
    const _id = req.body._id;

    Salvar({
      model: DenunciasModel,
      _id: _id,
      data: req.body,
      success: (denuncia) => {
        res.status(201).json({
          denuncia: denuncia,
          sucesso: true,
          mensagem: 'denuncia atualizado com sucesso!'
        });
      },
      error: (err) => {
        res.status(400).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível salvar esta denuncia'
        });
      }
    });
  };

  return controller;
};

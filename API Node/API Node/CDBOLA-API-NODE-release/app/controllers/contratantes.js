import { config } from '../../config'
import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const ContratantesModel = app.models.contratantes;
  const configRetorno = {
    senha: 0,
    __v: 0
  };

  controller.listarContratantes = (req, res) => {
    Listar({
      model: ContratantesModel,
      config: configRetorno,
      populate: ['goleiro', 'arbitro'],
      success: (contratantes) => {
        res.status(200).json(contratantes);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os contratantes'
        });
      }
    });
  };

  controller.obterContratante = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: ContratantesModel,
      _id: _id,
      config: configRetorno,
      populate: ['goleiro', 'arbitro'],
      success: (contratante) => {
        res.status(200).json(contratante);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o contratante!'
        });
      }
    });
  };

  controller.deletarContratante = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: ContratantesModel,
      _id: _id,
      success: (contratante) => {
        res.status(200).json({
          contratante,
          sucesso: true,
          mensagem: 'contratante deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este contratante!'
        });
      }
    });
  };

  controller.atualizarAvatar = (req, res) => {
    const _id = req.params.id;
    const avatar = `https://cdbola.com.br/uploads/${req.file.originalname}`;

    Salvar({
      model: ContratantesModel,
      _id: _id,
      data: {
        avatar
      },
      success: (contratante) => {
        res.status(201).json({
          sucesso: true,
          mensagem: 'contratante atualizado com sucesso!'
        });
      },
      error: (err) => {
        res.status(400).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este contratante'
        });
      }
    });
  };

  controller.atualizarContratante = (req, res) => {
    const _id = req.body.id;

    ContratantesModel.findByIdAndUpdate(_id, req.body).exec().then((contratante) => {
      res.status(201).json({
        success: true,
        message: 'Contratante atualizado com sucesso'
      });
    }).catch((err) => {
      res.status(201).json({
        success: false,
        message: 'Não foi possível atualizar o contratante, tente novamente'
      });
    });
  };

  return controller;
};

import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const ContratantesModel = app.models.contratantes;
  const ArbitrosModel = app.models.arbitros;
  const configRetorno = {
    __v: 0
  }

  controller.listarArbitros = (req, res) => {
    Listar({
      model: ArbitrosModel,
      config: configRetorno,
      populate: ['contratante'],
      success: (arbitro) => {
        res.status(200).json(arbitro);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar os arbitros'
        });
      }
    });
  };

  controller.obterArbitro = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: ArbitrosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante'],
      success: (arbitro) => {
        res.status(200).json(arbitro);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter o arbitro!'
        });
      }
    });
  };

  controller.deletarArbitro = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: ArbitrosModel,
      _id: _id,
      success: (arbitro) => {
        res.status(200).json({
          arbitro: arbitro,
          sucesso: true,
          mensagem: 'arbitro deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este arbitro!'
        });
      }
    });
  };

  controller.salvarArbitro = (req, res) => {
    const _id = req.body._id;

    if(_id) {
      ArbitrosModel.findByIdAndUpdate(_id, req.body).exec().then(arbitro => {
        res.status(201).json({
          arbitro: arbitro,
          sucesso: true,
          mensagem: 'Arbitro atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este arbitro'
        });
      });
    }else {
      const idContratante = req.body.contratante;

      ContratantesModel.findOne({_id: idContratante}).exec().then((arbitro) => {
        ArbitrosModel.create(req.body).then(arbitro => {
          ContratantesModel.findOne({_id: arbitro.contratante._id}).exec().then((contratante) => {
            contratante.arbitro = arbitro._id;
            contratante.save();
            res.status(201).json({
              arbitro: arbitro,
              sucesso: true,
              mensagem: 'arbitro salvo com sucesso!'
            });
          });
        }).catch((err) => {
          res.status(404).json({
            sucesso: false,
            erro: err,
            mensagem: 'Não foi possível salvar o arbitro'
          });
        });
      }).catch((err) => {
        res.status(400).json({
          arbitro: null,
          sucesso: false,
          mensagem: 'Id não encontrado',
          err: err
        });
      });
    }
  };

  controller.buscarPorApelido = (req, res) => {
    const apelido = req.params.apelido;
    const regex = new RegExp(`${apelido}`, 'gi');

    ContratantesModel.find({apelido: regex, arbitro: {$exists: true}}).populate('arbitro').exec().then((arbitros) => {
      res.status(200).json(arbitros);
    }).catch((err) => {
      res.status(200).json({
        sucesso: true,
        erro: err,
        mensagem: 'Não foi possível buscar os arbitros'
      });
    });
  };

  return controller;
};

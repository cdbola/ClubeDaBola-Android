import { Listar, Obter, Deletar, Salvar } from '../helpers'

module.exports = (app) => {
  const controller = {};
  const ContratantesModel = app.models.contratantes;
  const GoleirosModel = app.models.goleiros;
  const configRetorno = {
    __v: 0
  }

  controller.listarGoleiros = (req, res) => {
    Listar({
      model: GoleirosModel,
      config: configRetorno,
      populate: ['contratante'],
      success: (goleiros) => {
        res.status(200).json(goleiros);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível listar as goleiros'
        });
      }
    });
  };

  controller.obterGoleiro = (req, res) => {
    const _id = req.params.id;

    Obter({
      model: GoleirosModel,
      _id: _id,
      config: configRetorno,
      populate: ['contratante'],
      success: (goleiro) => {
        res.status(200).json(goleiro);
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível obter a goleiro!'
        });
      }
    });
  };

  controller.deletarGoleiro = (req, res) => {
    const _id = req.params.id;

    Deletar({
      model: GoleirosModel,
      _id: _id,
      success: (goleiro) => {
        res.status(200).json({
          goleiro: goleiro,
          sucesso: true,
          mensagem: 'Goleiro deletado com sucesso!'
        });
      },
      error: (err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível deletar este goleiro!'
        });
      }
    });
  };

  controller.salvarGoleiro = (req, res) => {
    const _id = req.body._id;

    if(_id) {
      GoleirosModel.findByIdAndUpdate(_id, req.body).exec().then(goleiro => {
        res.status(201).json({
          goleiro: goleiro,
          sucesso: true,
          mensagem: 'goleiro atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este goleiro'
        });
      });
    }else {
      const idContratante = req.body.contratante;

      ContratantesModel.findOne({_id: idContratante}).exec().then((arbitro) => {
        GoleirosModel.create(req.body).then(goleiro => {
          ContratantesModel.findOne({_id: goleiro.contratante._id}).exec().then((contratante) => {
            contratante.goleiro = goleiro._id;
            contratante.save();
            res.status(201).json({
              goleiro: goleiro,
              sucesso: true,
              mensagem: 'goleiro salvo com sucesso!'
            });
          });
        }).catch((err) => {
          res.status(404).json({
            sucesso: false,
            erro: err,
            mensagem: 'Não foi possível salvar o goleiro'
          });
        });
      }).catch((err) => {
        res.status(400).json({
          goleiro: null,
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

    ContratantesModel.find({apelido: regex, goleiro: {$exists: true}}).populate('goleiro').exec().then((goleiros) => {
      res.status(200).json(goleiros);
    }).catch((err) => {
      res.status(200).json({
        sucesso: true,
        erro: err,
        mensagem: 'Não foi possível buscar os goleiros'
      });
    });
  };

  return controller;
};

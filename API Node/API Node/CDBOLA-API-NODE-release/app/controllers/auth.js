import jwt from 'jsonwebtoken';
import hbs from 'nodemailer-express-handlebars';
import nodemailer from 'nodemailer';
import { config } from '../../config';
import bcrypt from 'bcrypt';
import async from 'async';

module.exports = (app) => {
  const email = config.recuperar.email;
  const senha = config.recuperar.senha;
  const controller = {};
  const ContratantesModel = app.models.contratantes;


  const smtpTransport = nodemailer.createTransport({
    service: config.recuperar.provedor,
    host: config.recuperar.host,
    auth: {
      user: config.recuperar.email,
      pass: config.recuperar.senha
    }
  });

  const handlebarsOptions = {
    viewEngine: 'handlebars',
    viewPath: './app/templates/',
    extName: '.html'
  };

  smtpTransport.use('compile', hbs(handlebarsOptions));


  controller.logout = (req, res) => {
    req.logout();
    res.status(200).json({success: true});
  }

  controller.recuperarSenha = function(req, res) {
    async.waterfall([
      function(done) {
        ContratantesModel.findOne({
          email: req.params.email
        }).exec(function(err, user) {
          if (user) {
            done(err, user);
          } else {
            done('User not found.');
          }
        });
      },
      function(user, done) {
        let novaSenha = Math.random().toString(36).slice(-8);
        ContratantesModel.findOne({_id: user._id}).exec().then(contratante => {
          contratante.senha = ContratantesModel().generateHash(novaSenha);
          contratante.save((err, contratante) => {
            done(err, contratante, novaSenha);
          });
        });
      },
      function(user, novaSenha, done) {
        const data = {
          to: user.email,
          from: email,
          template: 'recuperar-senha',
          subject: 'Recuperar Senha Clube da Bola!',
          context: {
            name: user.nome,
            senha: novaSenha
          }
        };

        smtpTransport.sendMail(data, function(err) {
          if (!err) {
            return res.json({
              success: true,
              message: 'Email enviado com sucesso!'
            });
          } else {
            return done(err);
          }
        });
      }
    ], function(err) {
      return res.status(422).json({ message: err });
    });
  };

  controller.resetarSenha = (req, res) => {
    const contratanteId = req.body.contratante;
    const novaSenha = req.body.senha;

    ContratantesModel.findOne({_id: contratanteId}).exec().then(contratante => {
      contratante.senha = ContratantesModel().generateHash(novaSenha);
      contratante.save((err, contratante) => {
        if (err){
          res.status(400).json({
            sucesso: false,
            erro: err,
            mensagem: 'Não foi possível salvar o contratante'
          });
        }else {
          res.status(201).json({
            sucesso: true,
            mensagem: 'Contratante salvo com sucesso!'
          });
        }
      });
    });
  };

  controller.login = (req, res) => {
    const email = req.body.email;
    const senha = req.body.senha;

    ContratantesModel.findOne({email: email}).exec().then((contratante) => {
      if (contratante == null || !ContratantesModel().validPassword(senha, contratante.senha ? contratante.senha : '')){
        res.status(401).json({
          contratante: null,
          token: null,
          success: false,
          message: "Não autorizado"
        });
      }else {
        req.session.contratante = contratante;
        var token = jwt.sign(contratante.email, app.get('secret'));
        res.status(200).json({
          contratante: {
            _id: contratante._id,
            nome: contratante.nome,
            apelido: contratante.apelido,
            dataNascimento: contratante.dataNascimento,
            email: contratante.email,
            _created: contratante._created,
          },
          token: token,
          success: true,
          message: "Autorizado"
        });
      }
    }, (err) => {
      console.log(err);
      res.status(500).json(err);
    });
  };

  controller.signup = (req, res) => {
    const _id = req.body._id;
    const _email = req.body.email;

    if(_id) {
      ContratantesModel.findByIdAndUpdate(_id, req.body).exec().then(contratante => {
        contratante.senha = ContratantesModel().generateHash(req.body.senha);
        contratante.save();
        res.status(201).json({
          contratante: contratante,
          sucesso: true,
          mensagem: 'Contratante atualizado com sucesso!'
        });
      }).catch((err) => {
        res.status(404).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível alterar este contratante'
        });
      });
    }else {
      ContratantesModel.find({email: _email}).exec().then(contratante => {
        if(contratante.length > 0)
          return res.status(400).json({sucesso: false, mensagem: 'Contratante já existe em nossa base'});

        const novoContratante = new ContratantesModel(req.body);
        novoContratante.senha = ContratantesModel().generateHash(novoContratante.senha);
        novoContratante.save((err, contratante) => {
          if (err){
            res.status(400).json({
              sucesso: false,
              erro: err,
              mensagem: 'Não foi possível salvar o contratante'
            });
          }else {
            res.status(201).json({
              sucesso: true,
              mensagem: 'Contratante salvo com sucesso!'
            });
          }
        });
      });
    }
  };


  controller.loginFacebook = (req, res) => {
    const email = req.body.email;

    ContratantesModel.findOne({email: email}).exec().then((contratante) => {
      if (contratante) {
        req.session.contratante = contratante;
        let token = jwt.sign(contratante.email, app.get('secret'));
        res.status(200).json({
          contratante: {
            _id: contratante._id,
            nome: contratante.nome,
            apelido: contratante.apelido,
            dataNascimento: contratante.dataNascimento,
            email: contratante.email,
            _created: contratante._created,
          },
          token: token,
          success: true,
          message: "Autorizado"
        });
      }
      else {
        const novoContratante = new ContratantesModel(req.body);
        novoContratante.save((err, contratante) => {
          if (err){
            res.status(400).json({
              sucesso: false,
              erro: err,
              mensagem: 'Não foi possível salvar o contratante'
            });
          }else {
            req.session.contratante = contratante;
            let token = jwt.sign(contratante.email, app.get('secret'));
            res.status(201).json({
              contratante: {
                _id: contratante._id,
                nome: contratante.nome,
                apelido: contratante.apelido,
                dataNascimento: contratante.dataNascimento,
                email: contratante.email,
                _created: contratante._created,
              },
              token: token,
              success: true,
              message: "Contratante salvo com sucesso"
            });
          }
        });
      }
    }, (err) => {
      console.log(err);
      res.status(500).json(err);
    });
  };

  return controller;
};

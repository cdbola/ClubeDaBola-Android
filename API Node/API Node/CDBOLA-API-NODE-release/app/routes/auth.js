import { config } from '../../config';
import jwt from 'jsonwebtoken';

module.exports = (app) => {

  const controller = app.controllers.auth;

  app.route(`${config.api}/contratantes/auth/logout`)
    .get(controller.logout);

  app.route(`${config.api}/contratantes/auth/login`)
    .post(controller.login);

  app.route(`${config.api}/contratantes/auth/signup`)
    .post(controller.signup);

   app.route(`${config.api}/contratantes/auth/recuperar/:email`)
    .get(controller.recuperarSenha);

  app.route(`${config.api}/contratantes/auth/resetar`)
    .post(controller.resetarSenha);

  app.route(`${config.api}/contratantes/auth/login/facebook`)
    .post(controller.loginFacebook);
};

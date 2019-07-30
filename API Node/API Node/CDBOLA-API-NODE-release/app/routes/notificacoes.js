import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.notificacoes;

  app.route(`${config.api}/notificacoes`)
    .get(controller.listarNotificacoes)
    .post(controller.salvarNotificacao);

  app.route(`${config.api}/notificacoes/:id`)
    .get(controller.obterNotificacao)
    .delete(controller.deletarNotificacao);
};

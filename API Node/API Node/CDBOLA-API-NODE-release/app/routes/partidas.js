import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.partidas;

  app.route(`${config.api}/partidas`)
    .get(controller.listarPartidas)
    .post(controller.salvarPartida);

  app.route(`${config.api}/partidas/:id`)
    .get(controller.obterPartida)
    .delete(controller.deletarPartida);

  app.route(`${config.api}/partidas/recentes/:contratanteId`)
    .get(controller.listarPartidasRecentes);

  app.route(`${config.api}/partidas/recentes/contratado/:contratanteId`)
    .get(controller.listarPartidasRecentesContratado);

  app.route(`${config.api}/partidas/passadas/:contratanteId`)
    .get(controller.listarPartidasPassadas);

  app.route(`${config.api}/partidas/passadas/contratado/:contratanteId`)
    .get(controller.listarPartidasPassadasContratado);

  app.route(`${config.api}/partidas/vincular`)
    .post(controller.vincular);

  app.route(`${config.api}/partidas/:id/desvincular`)
    .delete(controller.desvincular);

  app.route(`${config.api}/partidas/avaliar`)
    .put(controller.avaliar);
};

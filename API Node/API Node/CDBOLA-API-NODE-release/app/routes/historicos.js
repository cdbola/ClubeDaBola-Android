import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.historicos;

  app.route(`${config.api}/historicos`)
    .get(controller.listarHistoricos)
    .post(controller.salvarHistorico);

  app.route(`${config.api}/historicos/:id`)
    .get(controller.obterHistorico)
    .delete(controller.deletarHistorico);
};

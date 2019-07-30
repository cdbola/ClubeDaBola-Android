import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.rankings;

  app.route(`${config.api}/ranking/arbitros`)
    .get(controller.listarArbitros)

  app.route(`${config.api}/ranking/goleiros`)
    .get(controller.listarGoleiros);

  app.route(`${config.api}/ranking/todos`)
    .get(controller.listarTodos);
};

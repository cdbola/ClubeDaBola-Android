import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.osSelecionados;

  app.route(`${config.api}/selecionados`)
    .get(controller.listarSelecionados);

  app.route(`${config.api}/selecionados/:id`)
    .get(controller.obterSelecionado)
    .delete(controller.deletarSelecionado);
};

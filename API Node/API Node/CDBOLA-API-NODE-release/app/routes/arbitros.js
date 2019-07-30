import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.arbitros;

  app.route(`${config.api}/arbitros`)
    .get(controller.listarArbitros)
    .post(controller.salvarArbitro);

  app.route(`${config.api}/arbitros/:id`)
    .get(controller.obterArbitro)
    .delete(controller.deletarArbitro);

  app.route(`${config.api}/arbitros/buscar/:apelido`)
    .get(controller.buscarPorApelido);
};

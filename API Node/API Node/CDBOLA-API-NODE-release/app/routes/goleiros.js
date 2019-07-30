import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.goleiros;

  app.route(`${config.api}/goleiros`)
    .get(controller.listarGoleiros)
    .post(controller.salvarGoleiro);

  app.route(`${config.api}/goleiros/:id`)
    .get(controller.obterGoleiro)
    .delete(controller.deletarGoleiro);

  app.route(`${config.api}/goleiros/buscar/:apelido`)
    .get(controller.buscarPorApelido);
};

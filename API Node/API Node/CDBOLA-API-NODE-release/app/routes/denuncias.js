import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.denuncias;

  app.route(`${config.api}/denuncias`)
    .get(controller.listarDenuncias)
    .post(controller.salvarDenuncia);

  app.route(`${config.api}/denuncias/:id`)
    .get(controller.obterDenuncia)
    .delete(controller.deletarDenuncia);
};

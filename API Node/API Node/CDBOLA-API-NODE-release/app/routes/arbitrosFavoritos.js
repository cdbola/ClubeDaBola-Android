import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.arbitrosFavoritos;

  app.route(`${config.api}/arbitrosFavoritos/`)
    .get(controller.listarArbitrosFavoritos)
    .post(controller.salvarArbitroFavorito);

  app.route(`${config.api}/arbitrosFavoritos/:id`)
    .get(controller.obterArbitroFavorito)
    .delete(controller.deletarArbitroFavorito);
};

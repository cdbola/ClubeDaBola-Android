import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.goleirosFavoritos;

  app.route(`${config.api}/goleirosFavoritos/`)
    .get(controller.listarGoleirosFavoritos)
    .post(controller.salvarGoleiroFavorito);

  app.route(`${config.api}/goleirosFavoritos/:id`)
    .get(controller.obterGoleiroFavorito)
    .delete(controller.deletarGoleiroFavorito);
};

import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.bloqueados;

  app.route(`${config.api}/bloqueados/`)
    .get(controller.listarBloqueados)
    .post(controller.salvarBloqueado);

  app.route(`${config.api}/bloqueados/:id`)
    .get(controller.obterBloqueado)
    .delete(controller.deletarBloqueado);
};

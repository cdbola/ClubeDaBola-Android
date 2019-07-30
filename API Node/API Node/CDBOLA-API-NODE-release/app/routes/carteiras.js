import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.carteiras;

  app.route(`${config.api}/carteiras`)
    .get(controller.listarCarteiras)
    .post(controller.salvarCarteira);

  app.route(`${config.api}/carteiras/:id`)
    .get(controller.obterCarteira)
    .delete(controller.deletarCarteira);

  app.route(`${config.api}/resgatar/carteira`)
    .post(controller.resgatarCarteira);

  app.route(`${config.api}/carteira/detail/:id`)
    .get(controller.getCarteira);
};

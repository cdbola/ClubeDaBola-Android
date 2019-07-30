import { config } from '../../config';

module.exports = (app) => {
  const controller = app.controllers.moip;

  app.route(`${config.api}/customers`)
    .get(controller.listarCustomers)
    .post(controller.criarCustomer);

  app.route(`${config.api}/customers/:id`)
    .get(controller.obterCustomer);


  app.route(`${config.api}/creditCards`)
    .post(controller.criarCreditCards);

  app.route(`${config.api}/creditCards/:id`)
    .delete(controller.deletarCreditCards);


  app.route(`${config.api}/orders`)
    .get(controller.listarOrders);

  app.route(`${config.api}/orders/:id`)
    .get(controller.obterOrder);


  app.route(`${config.api}/payments`)
    .post(controller.criarPayment);

  app.route(`${config.api}/payments/:id`)
    .get(controller.obterPayment);
};

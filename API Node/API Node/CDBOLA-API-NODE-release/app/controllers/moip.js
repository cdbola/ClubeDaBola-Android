module.exports = (app) => {
  const moip = app.get('moip');
  const ContratantesModel = app.models.contratantes;
  const controller = {};

  // CUSTOMERS
  controller.criarCustomer = async (req, res) => {
    const contratanteId = req.body.ownId;
    let customerId = undefined;
    let Contratante;
    try {
      let obterContratante = await ContratantesModel.find({_id: contratanteId}).exec();
      Contratante = obterContratante[0];

      if (Contratante.customerId) {
        customerId = Contratante.customerId;
      }
    } catch (e) {
      customerId = undefined;
    }

    if (!customerId) {
      moip.customer.create(req.body).then((response) => {
        Contratante.customerId = response.body.id;
        Contratante.save();
        res.status(201).json({
          contratante: Contratante,
          customer: response.body,
          sucesso: true,
          mensagem: 'Customer criado com sucesso'
        });
      }).catch((err) => {
        res.status(400).json({
          sucesso: false,
          erro: err,
          mensagem: 'Não foi possível criar o customer'
        });
      })
    } else {
      res.status(400).json({
        sucesso: false,
        mensagem: 'Customer ID Já existe'
      });
    }
  };

  controller.obterCustomer = (req, res) => {
    const customerId = req.params.id;

    moip.customer.getOne(customerId)
    .then((response) => {
      res.status(200).json(response);
    })
    .catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível obter o customer'
      });
    })
  };

  controller.listarCustomers = (req, res) => {

    moip.customer.getAll()
    .then((response) => {
      res.status(200).json(response.body);
    }).catch((err) => {
      console.log(err)
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível obter o customer'
      });
    })
  };

  // CARTOES DE CRÉDITO
  controller.criarCreditCards = (req, res) => {
    const customerId = req.body.customerId;
    const creditCard = req.body.creditCard;

    moip.customer.createCreditCard(customerId, {
      method: "CREDIT_CARD",
      creditCard
    }).then((response) => {
      res.status(201).json({
        customer: response.body,
        sucesso: true,
        mensagem: 'credit card criado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível criar o credit card'
      });
    })
  };

  controller.deletarCreditCards = (req, res) => {
    const creditcardId = req.params.id;

    moip.customer.removeCreditCard(creditcardId)
    .then((response) => {
      res.status(201).json({
        sucesso: true,
        mensagem: 'credit card deletado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível deletar o credit card'
      });
    })
  };

  // ORDERS
  controller.criarOrder = (req, res) => {
    moip.order.create(req.body).then((response) => {
      res.status(201).json({
        order: response.body,
        sucesso: true,
        mensagem: 'order criado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        mensagem: 'não foi possível criar o order'
      });
    })
  };

  controller.listarOrders = (req, res) => {
    moip.order.getAll()
      .then((response) => {
        res.status(200).json(response.body);
      }).catch((err) => {
        res.status(400).json({
          sucesso: false,
          mensagem: 'não foi listar os orders'
        });
      })
  };

  controller.obterOrder = (req, res) => {
    const orderId = req.params.id;

    moip.order.getOne(orderId)
      .then((response) => {
        res.status(200).json(response.body);
      }).catch((err) => {
        res.status(400).json({
          sucesso: false,
          mensagem: 'não foi possível obter o order'
        });
      })
  };

  // PAYMENTS
  controller.criarPayment = (req, res) => {
    const orderId = req.body.orderId;
    const payment = req.body.payment;

    moip.payment.create(orderId, payment).then((response) => {
      res.status(201).json({
        sucesso: true,
        mensagem: 'payment criado com sucesso!'
      });
    }).catch((err) => {
      res.status(400).json({
        sucesso: false,
        erro: err,
        mensagem: 'Não foi possível criar o payment'
      });
    })
  };

  controller.obterPayment = (req, res) => {
    const paymentId = req.params.id;

    moip.payment.getOne(paymentId)
    .then((response) => {
      res.status(200).json(response.body);
    }).catch((err) => {
      console.log(err);
    })
  };

  return controller;
}

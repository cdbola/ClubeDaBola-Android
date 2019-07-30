import express from 'express';
import bodyParser from 'body-parser';
import methodOverride from 'method-override';
import helmet from 'helmet';
import load from 'express-load';
import { config } from './';
import passport from 'passport';
import session  from 'express-session';
import swaggerUi from 'swagger-ui-express';
import swaggerDocument from '../swagger.json';
import moipSdk from 'moip-sdk-node';
import compression from 'compression';
import moment from 'moment';
import * as firebase from 'firebase-admin';

module.exports = () => {
  const app = express();

  if(process.env.NODE_ENV == 'production'){
    swaggerDocument.host = 'cdbola.herokuapp.com';
  }else{
    swaggerDocument.host = 'localhost:3000';
  }

  const moip = moipSdk({
    // accessToken: 'your-access-token',
    token: '1CKZMI9D3BXOG47HMQYWUSJVS43ISTDU',
    key: 'VZV8BYBM4UKWHK5P3JPEKXYJTCYFENABV5NL0CL2',
    production: true
  })

  // const moip = moipSdk({
  //   // accessToken: 'your-access-token',
  //   token: 'Y1NHAJGXWAD7O7DPMJPY75FRG35U17PI',
  //   key: 'ZOXCMRRF5RIHMZGACQZYAGLFBNWCIX4FT9HO8OLU',
  //   production: false
  // })

  var options = {
    explorer : false,
    customCss: `
      .swagger-ui .topbar {
        background: #000
      }
      .swagger-ui .topbar-wrapper a img {
        display: none;
      }
      .swagger-ui .topbar-wrapper a::before {
        content: "";
        width: 30px;
        height: 30px;
        background: url(https://imagepng.org/wp-content/uploads/2017/10/bola.png) no-repeat;
        background-size: 100%;
      }
      .swagger-ui .topbar-wrapper a span {
        display: none;
      }
      .swagger-ui .topbar-wrapper a::after {
        content: 'Clube da Bola';
        font-size: 22px;
        margin: 5px 11px;
        position: relative;
        top: 3px;
      }
    `
  };

  firebase.initializeApp({
    credential: firebase.credential.cert(config.firebase.serviceAccount),
    databaseURL: config.dataBase
  });

  app.set('firebase', firebase);
  app.set('moment', moment);
  app.set('moip', moip);

  app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument, options));
  app.use(compression());
  app.use(express.static('public'));
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded(config.urlencoded));
  app.use(function(req, res, next){
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'PUT, GET, POST, DELETE, OPTIONS');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, x-access-token');
    res.header('Access-Control-Allow-Credentials', true);
    next();
  });
  app.use(methodOverride());
  app.use(helmet());
  app.use(helmet.hidePoweredBy({ setTo: 'PHP 5.0.0' }));
  app.disable('x-powered-by');
  app.use(helmet.ieNoOpen());

  // required for passport
  app.set('secret', config.session.secret);
  app.use(session(config.session));
  app.use(passport.initialize());
  app.use(passport.session());

  load('models', {cwd: 'app'})
    .then('controllers')
    .then('../app/routes/index')
    .then('routes')
    .into(app);

  return app;
};

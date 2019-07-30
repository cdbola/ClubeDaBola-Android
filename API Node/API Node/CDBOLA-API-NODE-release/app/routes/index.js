import { config } from '../../config';
import jwt from 'jsonwebtoken';

module.exports = (app) => {
  app.use((req, res, next) => {
    if(req.url.indexOf('/recuperar') != -1 || req.url.indexOf('/uploads') != -1 || req.url.indexOf('/json') != -1 || req.url.indexOf('/signup') != -1 || req.url.indexOf('/api-docs') != -1 || req.url.indexOf('/login') != -1 || req.url.indexOf('/logout') != -1) {
      next();
    }else {
      let token = req.params.token || req.body.token || req.query.token || req.headers['authorization'] || req.headers['Authorization'];

      if (token) {
        jwt.verify(token, app.get('secret'), (err, decoded) => {
          if (err) {
            return res.status(401).json({ success: false, message: 'Falha na autenticação' });
          } else {
            req.decoded = decoded;
            next();
          }
        });
      } else {
        return res.status(401).send({
          success: false,
          message: 'Sem token fornecido'
        });
      }
    }
  });
};

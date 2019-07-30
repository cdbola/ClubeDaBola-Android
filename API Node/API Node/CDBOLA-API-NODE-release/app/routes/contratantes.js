import { config } from '../../config';
import multer from 'multer';

var FTPStorage = require('multer-ftp');
var path = require('path');

module.exports = (app) => {
  // const storage = multer.diskStorage({
  //   destination: function (req, file, cb) {
  //     cb(null, 'public/uploads/');
  //   },
  //   filename: function (req, file, cb) {
  //     cb(null, file.originalname);
  //   }
  // });

  // const storage = sftpStorage({
  //     sftp: {
  //       host: 'srv155.main-hosting.eu',
  //       port: 21,
  //       username: 'u257155770',
  //       password: 'cdbola123'
  //     },
  //     destination: function (req, file, cb) {
  //       cb(null, '/public_html/uploads');
  //     },
  //     filename: function (req, file, cb) {
  //       cb(null, file.originalname);
  //     }
  // });



  var upload = multer({
  storage: new FTPStorage({
    basepath: '/public_html/uploads',
    destination: function (req, file, options, callback) {
       callback(null, path.join(options.basepath, file.originalname))
    },
    ftp: {
      host: 'srv155.main-hosting.eu',
      secure: true, // enables FTPS/FTP with TLS
      user: 'u257155770',
      password: 'cdbola123'
    }
  })
  });
  
  const controller = app.controllers.contratantes;





  app.route(`${config.api}/contratantes`)
    .get(controller.listarContratantes)
    .put(controller.atualizarContratante);

  app.route(`${config.api}/contratantes/:id`)
    .get(controller.obterContratante)
    .delete(controller.deletarContratante);

  app.route(`${config.api}/contratantes/:id/atualizarAvatar`)
    .patch(upload.single('file'), controller.atualizarAvatar);
};

import http from 'http';
import app from './config/express';
import { config } from './config';

if(process.env.NODE_ENV == 'production'){
  require('./config/database')('mongodb://admin:admin123@ds121906.mlab.com:21906/cdbola');
}else{
  // require('./config/database')('mongodb://admin:admin123@ds121906.mlab.com:21906/cdbola');
  require('./config/database')('mongodb://localhost/cdbola');
}

const server = http.createServer(app());
 //const controller = app.controllers.contratantes;


var cron = require('node-cron');
 
	



server.listen(process.env.PORT || config.port, () => {
  console.log(`Servidor rodando na porta ${config.port}`);
 //  cron.schedule('*/5 * * * * *', () => {
 //  		console.log('running every minute 1, 2, 4 and 5');
	// });
});

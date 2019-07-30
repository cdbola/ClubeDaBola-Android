const LocalStrategy   = require('passport-local').Strategy;
const ContratanteModel = require('../app/models/contratantes');

module.exports = function(app, passport) {

    passport.serializeUser(function(contratante, done) {
        done(null, contratante.id);
    });

    passport.deserializeUser(function(id, done) {
        ContratanteModel.findById(id, function(err, contratante) {
            done(err, contratante);
        });
    });

    passport.use('contratante-signup', new LocalStrategy({
        nome: 'nome',
        apelido: 'apelido',
        dataNascimento: 'dataNascimento',
        email: 'email',
        senha: 'senha',
        passReqToCallback : true
    }, function(req, email, senha, done) {
        debugger
        process.nextTick(function() {
            ContratanteModel.findOne({ 'email' :  email }, function(err, contratante) {
                if (err)
                    return done(err);

                if (contratante) {
                    return done(null, false, req.flash('signupMessage', 'That email is already taken.'));
                } else {
                    const novoContratante = new ContratanteModel();

                    novoContratante.email = email;
                    novoContratante.senha = novoContratante.generateHash(senha);

                    novoContratante.save(function(err) {
                        if (err)
                            throw err;
                        return done(null, novoContratante);
                    });
                }
            });
        });
    }));
};

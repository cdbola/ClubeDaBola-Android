export const config = {
  port: 3000,
  urlencoded: {
    extended: true
  },
  host: process.env.NODE_ENV == 'production' ? 'https://cdbola.herokuapp.com' : 'http://localhost:3000',
  api: '/api/v1',
  session: {
    secret: 'iloveappscdbola',
    resave: false,
    saveUnInitialize: true,
    proxy: true
  },
  recuperar: {
    email: 'no-reply@cdbola.com.br',
    senha: 'banmfcpu',
    provedor: 'hostinger',
    host: 'smtp.hostinger.com.br'
  },
  firebase: {
    serviceAccount: {
      type: "service_account",
      project_id: "cd-bola",
      private_key_id: "ce14db7236e197cbf5096f87fc47f4c6da7c03d9",
      private_key: "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZ/3RrphMSEev9\nGf5b9cJW8EnnMT+9iek0yLvL57goys3kXWMdLZ/4kyN8aGpWRXKQTQVeevkQNwPV\nybXQ/zI6bMSQ3umBibScl5iXW6xCpwiyG/X2c8GDzlPAk0KI/Xc6vuSj2GFyZE5L\njpGGY2XX9N+yaz1A8a7Sj2JUZrQ6iWy7G6/8xMvXSulGhLQpYmcjGLGANnuwZCZo\nr3WWRcPq5WxwEb4NggE+CZ60JMpaGI3S4JHcx8M5VMnEdhVEASPxsSkQ3y9mm2O+\nsgdZLbVqJqs5wShMYXEvG3XD7w1v893LgJ+ACwOyO+PWPI6FMriQfVvgCHdevCHn\nqGOOcAPxAgMBAAECggEAFugpVKPw/USeMllt/Rba8ByxGYs5XIPJpTjgum1+9s5n\n/vqnNh1lyM92PVi4zJlq960FSe8H4lIUp3XoLizvt0IboSMPX93weESShZ00y/tV\nr0y6cwz38Ze83I3rHT56wEnJ79dNc3sZLGMKIY/ig9OgfMU36NOqehaq/TwDvhsc\n9bGCj0p227dQIW72fxAV/JCGth12j62+EgOiJbq4P+sWoHnJoQbgDOGQIIHL38G5\nRQ5Bg27tp4S4qoSypBRZTIhH+ubZqGST00NwEDNo8Q7UFiUF+MWSBOeGUtKDqiSI\n3Jvo8MW4K4UD75iOp8UpFPoTev3HzuYks11JDrz9ywKBgQDKDxY+NJNnpvCQbLaK\ni2/88ZFtGolSsavYEeg/7seTt545KKinVm1SQXQRuEOIFoSm+PGrjpdqNerrMHB/\nMOpyV93q9sUYi1YCFnIE2x8EplR+EdV2wwfbshZwNj3KzmyhyMYpQHj3numPDDcg\nAGO758brxApQ1vgFWKLKn1eIUwKBgQDDG9NwG0fGRFUmyrq38qyS6AZpvF7mPLnc\nqfPp7DhSqdyHd+BcZvJpYGkJ/rR2LfMfGAZ+KiXeE5EmdbvlLUihlcrF9KH48IJx\nxa5cVsJjEwCWg3mQAd8EzpwUNflA7twseUzgMPqGoHC98jnkvBd04DJjbHzB5USF\nxV5EkleqKwKBgE4gXz1WHtipF8MABeSz3W00CkLhS8+JmyREaESwEsLu8GKxceSh\n6ksvu61OTe5Epq3Y+7iVvb33cwnVBOppA32Ks+ex2quO1IaiHxv5jZjCp1DHnH37\nEwLDbSGeF25fOmmjs9snbp5v92a0FmxtOiLhj2yBtGtlcyi+CNPHoJS5AoGAJH+8\nGijAUJLP6yXZsDexmPnW2Ujn5+JMUr9oZcZO8wfbvWvriN2pU5U5kSkY0VLfPF3e\nORPhaZ33dV5o/AJwp+nBuS5+bndvr6DvcU65ppcTcRS/VPVEpz2PbSh7rG4Cuj8m\n5nnDJBQ3gxEhS2z0ZmTaiEbvP8aH/w5hCCroQ2kCgYEAsg9hJZtD6R3m9qR4CqPu\nBpF5lENOknWaMzdxWsXIfjVz+z9qGuO0de16tr8cFrtuEM0m6aViMY0p/XGukbu8\n4o0u1kJAOzWgeiu+HTkSLOPpipV5WfzxfbRVLP+fAmZbfZILSO7+wKFe/VMZQJaP\neWnUUfs4zZ6AMhi3zyr9VQ4=\n-----END PRIVATE KEY-----\n",
      client_email: "firebase-adminsdk-kdvxu@cd-bola.iam.gserviceaccount.com",
      client_id: "100728397860205160790",
      auth_uri: "https://accounts.google.com/o/oauth2/auth",
      token_uri: "https://oauth2.googleapis.com/token",
      auth_provider_x509_cert_url: "https://www.googleapis.com/oauth2/v1/certs",
      client_x509_cert_url: "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-kdvxu%40cd-bola.iam.gserviceaccount.com"
    },
    dataBase: "https://cd-bola.firebaseio.com"
  }
};

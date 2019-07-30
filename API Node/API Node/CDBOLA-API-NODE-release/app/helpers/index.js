const verificaPopulate = ({ model, populate }) => {
  if (populate) {
    return model.populate(populate);
  }
  else {
    return model;
  }
};

export function Listar ({ model, config, populate, success, error }) {
  verificaPopulate({ model: model.find({}, config), populate }).exec().then((result) => {
    success(result);
  }).catch((err) => {
    error(err);
  });
}

export function Obter ({ model, config, _id, populate, success, error }) {
  verificaPopulate({ model: model.findById({_id: _id}, config ), populate }).exec().then((result) => {
    success(result);
  }).catch((err) => {
    error(err);
  });
}

export function Deletar ({ model, _id, success, error }) {
  model.remove({"_id": _id}).exec().then(result => {
    success(result);
  }).catch((err) => {
    error(err);
  });
}

export function Salvar ({ model, _id, data, success, error }) {
  if(_id) {
    model.findByIdAndUpdate(_id, data).exec().then(result => {
      success(result);
    }).catch((err) => {
      error(err);
    });
  }else {
    model.create(data).then(result => {
      success(result);
    }).catch((err) => {
      error(err);
    });
  }
}

export function timeConvert (num) {
  let hours = Math.floor(num / 60);
  let minutes = num % 60;
  return hours + ":" + minutes;
}

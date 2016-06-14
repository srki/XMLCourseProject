/**
 * Created by Srđan on 14.6.2016..
 */

var Sequelize = require('sequelize');

var sequelize = new Sequelize('db', '', '', {
    dialect: 'sqlite',
    storage: 'db.sqlite',
    //logging: null,
    define: {
        timestamps: false
    }
});

exports.init = function () {
    var db = {
        Act: sequelize.import(__dirname + '/act-model.js')
    };

    db.Act.sync();

    db.act = {
        create: function (act) {
            return db.Act.create(act, {});
        },
        query: function () {
            return db.Act.findAll({});
        }
    };
    
    return db;
};
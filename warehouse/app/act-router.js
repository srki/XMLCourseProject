/**
 * Created by Srđan on 14.6.2016..
 */

var express = require('express');

module.exports = function (db) {
    var router = express.Router();

    router.get('/', function (req, res, next) {
        return db.act.query().then(function (data) {
            res.json(JSON.stringify(data));
        });
    });

    router.post('/', function (req, res, next) {
        db.act.create(req.body).then(
            function () {
                res.status(204).send();
            },
            function () {
                res.status(400).send();
            });
    });

    return router;
};

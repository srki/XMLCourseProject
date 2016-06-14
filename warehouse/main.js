/**
 * Created by Srđan on 14.6.2016..
 */
/*global __dirname*/
var base = __dirname + '\\app';
var express = require('express'),
    bodyParser = require('body-parser'),
    serveStatic = require('serve-static');

var app = express();
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(serveStatic(__dirname + '/static'));

var database = require(base + '\\database.js');
var db = database.init();
var router = require(base + '\\act-router.js')(db);

app.use('/api/acts', router);

app.listen(8081);
console.log("server is running on port 8081");
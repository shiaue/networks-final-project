var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

function start (){
    require('dns').lookup(require('os').hostname(), function (err, ip_address, fam) {
        console.log('localhost: ' + ip_address + " PORT: " + port);
    });
}

var port     = process.env.PORT || 8080;

var app = express();

// view engine setup
app.set('views', path.join(__dirname, '/public'));
app.set('view engine', 'ejs');
app.use(express.static(__dirname + '/public'));

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());




// Routes

require('./routes/routes.js')(app);


app.listen(port);
start();

var appRouter = function(app) {


    var jsonContacts = {};

    app.get("/", function(req, res) {
        res.send("Hello World");
    });
    app.get("/status", function(req, res) {
        res.send("Connected");
        res.render();
        console.log("Endpoint established");
    });

    app.get("/contacts", function(req, res) {
        res.send({
            phone: "8479222186",
            message: "Hey what's up hello world: This text was sent from a node server!"

        });
    });

    app.get("/form", function(req,res)  {
       res.render("views/form");
    });

    app.post("/contacts", function(req, res) {
        console.log(req.body);
        jsonContacts = req.body;

        res.send(req.body);
    });

    // display a webpage
    // Build a list with the contacts
    //
    app.get("/show", function(req, res) {
       res.send(jsonContacts);
    });

    //another route for selected contact




    //app.get("/account", function(req, res) {
    //    var accountMock = {
    //        "username": "username",
    //        "password": "password",
    //        "twitter": "@twitter"
    //    };
    //
    //    if(!req.query.username) {
    //        return res.send({"status": "error", "message": "missing username"});
    //    } else if(req.query.username != accountMock.username) {
    //        return res.send({"status": "error", "message": "wrong username"});
    //    } else {
    //        return res.send(accountMock);
    //    }
    //});

    app.post("/account", function(req, res) {
        if(!req.body.username || !req.body.password || !req.body.twitter) {
            return res.send({"status": "error", "message": "missing a parameter"});
        } else {
            return res.send(req.body);
        }
    });

    app.post("/set", function(req, res) {
        if(!req.body.username || !req.body.password || !req.body.twitter) {
            return res.send({"status": "error", "message": "missing a parameter"});
        } else {
            return res.send(req.body);
        }
    });



};

module.exports = appRouter;
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
       res.render("form");
    });
    app.get("/test", function(req,res)  {
       res.render('test', { title: 'Hey', message: 'Hello there!'});
    });

    app.post("/contacts", function(req, res) {
        console.log(req.body);
        jsonContacts = req.body;

        //res.send(req.body);
        //res.send({
        //    phone: "8479222186",
        //    message: "Hey what's up hello world: This text was sent from a node server!"
        //
        //});
    });

    // display a webpage
    // Build a list with the contacts
    // Need to add functionality
    app.get("/showContacts", function(req, res) {
        var dataString = JSON.stringify(jsonContacts);
        console.log("JSON Object, non-stringified");
        console.log(jsonContacts);
        //console.log("Parsed JSON: " + JSON.parse(jsonContacts));

        res.render('contact', {data: jsonContacts});
    });

    //another route for selected contact






};

module.exports = appRouter;
function DataBaseRef(path) {
  var path = null;
  var database = null;
  this.path = '';
};

DataBaseRef.prototype.child = function(path) {
    this.path = this.path + '/' + path;
    return this;
};

DataBaseRef.prototype.on = function(destination, callback) {

    this.destination = destination;
    var url = '/topic' + this.path + '/' + destination;
    
    console.log('Subscribing to ' + url)
    this.database.client.subscribe(url, function (message) {
        // called when the client receives a STOMP message from the server
           if (message.body) {
            // alert("got message with body " + message.body)
           } else
           {
             alert("got empty message");
           }    
        callback(message);
    });
 
    if (destination == 'list') {
        // Perform the loading
        url = '/db' + this.path + '/' + destination;
        
        console.log('Initiating data [' + url + ']');
        this.database.client.send(url, {}, {});
    }
};

DataBaseRef.prototype.add = function(object) {
    
    var url = '/db' +  this.path + '/add';
    console.log('Saving [' + object + ']: ' + url);
    this.database.client.send(url, {}, object);
}

DataBaseRef.prototype.remove = function(object) {
    
    var url = '/db' +  this.path + '/remove';
    console.log('Removing [' + object + ']: ' + url);
    this.database.client.send(url, {}, object);
}


var Database = function(url) {

    var client = null;
    var endpoint = null;
    var connected = false;
    this.endpoint = url;
    
    return this;
};

Database.prototype.ref = function() {
    var ref = new DataBaseRef();
    ref.database = this;
    return ref;
};

Database.prototype.connect = function(callback) {
    
    if (!this.endpoint) {
        return
    }
    
    var socket = new SockJS(this.endpoint);
    this.client = Stomp.over(socket);
    
    this.client.connect({}, function (session) {
        
        this.connected = true;
            if (callback) {
                console.log('📞');
                callback(this.connected);
            }
    });
};

Database.prototype.disconnect = function(callback) {
    if (this.client !== null) {
            this.client.disconnect();
    }    
};

// Use the database
var database = null;
var cities = null;

function addObservers() {

    cities = database.ref().child('cities');
    
    cities.on('add', function(message) {
        console.log('📩 ADD: ' + message);
        var json = JSON.parse(message.body);
        addcity(json);
    });
    
    cities.on('list', function(message) {
        console.log('LIST: ' + message);
        var json = JSON.parse(message.body);
        for (var key in json) {
            addcity(json[key]);
        }
        
     
    });
    
    cities.on('remove', function(message) {
        console.log('REMOVE: ' + message);
        var json = JSON.parse(message.body);
        var selector = 'table#cities-table tr#' + json.cityId;
        $(selector).remove();
    });

}

function deletecity(cityId) {
    
    var city = JSON.stringify({'cityId': cityId});
    cities.remove(city);
}

function addcity(city) {
    var button = "<button onclick=\"deletecity('" + city.cityId + "');\" class=\"btn btn-danger btn-delete\" type=\"submit\">Delete</button>";
    $("#city-rows").append("<tr id=\"" + city.cityId  + "\"><td>" + city.cityId  + "</td><td>" + city.contractName + "</td><td>" + button + "</td></tr>");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#cities-table").show();
    } else {
        $("#cities-table").hide();
    }
    $("#city-rows").html("");
}


$(function () {
     $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function() {
            
        });
        $("#disconnect").click(function() {
            
        });

    database = new Database('/websocket');
    database.connect(function(connected) {
            if (connected) {
                addObservers();
            } else {
                console.log('Unable to connect!');
            }
    });
    
    
});
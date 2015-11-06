myApp.factory('parseService', function ($timeout, $state) {
    Parse.initialize("UScatQxieE3MNKjYZlfH5WvkuRQxz1rmcheGHtNL", "kCH4Hj79DAM8nCX0GQ7W7Zki0F4HyjNBPzRP3vHO");
    var data = [];
    var loc = [];
    var res = [];
    return {

        startWorking: function (scope, input) {

            scope.$emit('LOAD')

            var ParseObject = Parse.Object.extend("PARSE");
            var LocationObject = Parse.Object.extend("LOCATION");
            var parseObject = new ParseObject();
            var locationObject = new LocationObject();

            var query = new Parse.Query(ParseObject);
            var locationQuery = new Parse.Query(LocationObject);

            res = input.split(" ");

            // var res = '<span class="highlight">' + input.split(" ") + "</span>";

            query.find({
                success: function (list) {

                    if (data.length != 0) {
                        data.length = 0;
                    }
                    locationQuery.find({
                        success: function (locationList) {

                            for (var i = 0; i < list.length; i++) {
                                for (var j = 0; j < res.length; j++) {
                                    var parseObject = list[i];
                                    var locationObject = locationList[i];

                                    if (parseObject.get('FirstName').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1 ||
                                        parseObject.get('LastName').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1 ||
                                        parseObject.get('Address').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1 ||
                                        parseObject.get('Email').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1) {
                                        {
                                            if (data.indexOf(parseObject) == -1) {
                                                data.push(parseObject);
                                                loc.push(locationObject);
                                            }
                                        }
                                    }
                                }

                            }
                        },
                        error: function () {
                            document.write("<h2> Error 404: Innerquery not found</h2>");
                        }
                    });

                    scope.$emit('UNLOAD');
                    if (data.length != 0) {

                        return true;
                    } else {

                        return false;
                    }

                },
                error: function () {
                    document.write("<h2>Error 404: not found</h2>");
                }

            });


        }, //end startWorking()
        getUserData1: function () {
            return data;

        },
        getUserData2: function () {

            return loc;
        },
        getSearchString: function () {
            return res;
        }
    };

});
'use strict';
myApp.directive('myWidget', function (parseService) {
    return {
        scope: {
            text: '=',
            data: '='

        },
        link: function (scope, elements, attributes) {
            scope.displayList = function () {
                scope.data = parseService.getUserData1();
                var container = angular.element(document.querySelector('#list'));
                container.html('');
                var res;
                var factor;
                if (scope.data.length != 0) {
                    factor = true;
                } else {
                    factor = false;
                }
                if (factor == true) {
                    for (var i = 0; i < scope.data.length; i++) {
                        var items = angular.element("<li>");
                        res = "FirstName: " + scope.data[i].get("FirstName")
                            + "<br />" + "LastName: " + scope.data[i].get("LastName") + "<br />" + "Address: " + scope.data[i].get("Address")
                            + "<br />" + "Email: " + scope.data[i].get("Email")
                            + "<br />" + "Phone: " + scope.data[i].get("PhoneNumber");
                        items.append(res);

                        container.append(items);
                    }



                } else if (factor == false) {
                    container.html("No records found...");
                }
            };

            scope.displayMap = function () {
                var myLatLng1 = { lat: 14, lng: 77 };
                var marker;
                var address;
                var infoWindow = new google.maps.InfoWindow();
                var mapOptions = {
                    center: new google.maps.LatLng(13, 77),
                    zoom: 5,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("map"),
        mapOptions
        );
                scope.loc = parseService.getUserData2();
         
                for (var i = 0; i < scope.loc.length; i++) {

                    var lati = scope.loc[i].get("lat");
                    var lang = scope.loc[i].get("lang");
               
                    myLatLng1 = { lat: lati, lng: lang };
                    address = scope.data[i].get("Address").toString();

                    marker = new google.maps.Marker({ position: myLatLng1, map: map, title: address, animation: google.maps.Animation.DROP });

                    google.maps.event.addListener(marker, "click", (function (marker, i) {
                        return function () {
                            var userData = "FirstName: " + scope.data[i].get("FirstName")
                            + "<br />" + "LastName: " + scope.data[i].get("LastName") + "<br />" + "Address: " + $scope.data[i].get("Address")
                            + "<br />" + "Email: " + scope.data[i].get("Email")
                            + "<br />" + "Phone: " + scope.data[i].get("PhoneNumber");
                            infoWindow.setContent(
                "<div>" +
                   "<p><span style='font-weight:bold;'>Person: </span>" + userData + "</p>" +
                   "</div>"
            );
                            infoWindow.open(map, marker);
                        }
                    })(marker, i));

                }
            };
            scope.$on('displayList', function (event, data) {
                scope.displayList()

            });
            scope.$on('displayMap', function (event, data) {
                scope.displayMap();
            });
        },
        templateUrl: 'partials/oreo.html'
    }
});

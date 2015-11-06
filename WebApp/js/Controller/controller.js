'use strict';
myApp.controller('searchController', ['$scope', 'parseService', '$timeout', '$state', '$rootScope', function ($scope, parseService, $timeout, $state, $rootScope) {
    $scope.data = [];
    $scope.loc = [];
    var temp;
    $scope.foo = "hoho!";
    $scope.text = "yeha";
    $scope.$on('LOAD', function () {


        $scope.$parent.loading = true;
    });
    $scope.$on('UNLOAD', function () {

        $scope.$parent.loading = false;
    });
    $scope.submit = function () {

        // $scope.dataloaded = false;
        temp = parseService.startWorking($scope, $scope.name);
        if (!$state.is('/search')) {
            $timeout(function () {
                $state.go('/search');
            }, 1000);
        }
        if (temp != false) {
            $timeout(function () {

                $rootScope.$broadcast('displayList', {});
                $rootScope.$broadcast('displayMap', {});
                $scope.highlight();
            }, 2000);


        }

    }


    $scope.highlight = function () {
        var container = angular.element(document.querySelector('#list'));
        $scope.res = parseService.getSearchString();
        for (var i = 0; i < $scope.res.length; i++) {

            var reg = new RegExp($scope.res[i], "ig");
            var pattern = "<span class=\"highlight\">" + $scope.res[i] + "</span>";


            $('#list').children().each(function () {




                $(this).html($(this).html().replace(reg, pattern)); //change the text of the children

                //  children = children + $(this)[0].outerHTML; //copy the changed child
            });
        }

    }


} ]);
//end searchController
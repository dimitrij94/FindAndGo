angular.module("KeyStyle", ['ngRoute','wu.masonry']).controller("EmployeeController", ["$scope", "$http", "$route",
    function ($scope, $http, $route) {
        $scope.employees = [
            {
                id: 1,
                name: "Анастасія Захаровна",
                rating: 5,
                speciality: "Косметолог",
                photo: "http://placehold.it/50x50"
            },
            {
                id: 2,
                name: "Валентин Алексеев",
                rating: 2,
                speciality: "Дерматолог",
                photo: "http://placehold.it/50x50"
            }];
        $scope.comments = [
            {
                employeeId: 1,
                menuName: "Menu 1",
                comment: "Donec id elit non mi porta gravida at eget metus. Maecenas seddiam eget risus varius blandit.",
                rating: 4,
                user: "Angelika24"
            },
            {
                employeeId: 1,
                menuName: "Menu 2",
                comment: "Donec id elit non mi porta gravida at eget metus. Maecenas seddiam eget risus varius blandit.",
                rating: 5,
                user: "Aniya18"
            },
            {
                employeeId: 2,
                menuName: "Menu 1",
                comment: "Donec id elit non mi porta gravida at eget metus. Maecenas seddiam eget risus varius blandit.",
                rating: 2,
                user: "Konstantin"
            },
            {
                employeeId: 2,
                menuName: "Menu 2",
                comment: "Donec id elit non mi porta gravida at eget metus. Maecenas seddiam eget risus varius blandit.",
                rating: 3,
                user: "Valentin16"
            }
        ];
        $scope.menus = [
            {
                name: "Menu 1",
                price: 150,
                description: "С каждым днем растет число работников занятых в сфере услуг и распространении информации. Если символами прошлых  столетий былиферма и фабрика, то символ нынешнего XXI века – этоофис.",
                rating: 4,
                photo: "http://placehold.it/250x200",
                employeeId: 1,
                services: [
                    {
                        name: "Optional short description. Optional short description",
                        price: 25
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 40
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 400
                    }
                ]
            },
            {
                name: "Menu 2",
                price: 150,
                description: "С каждым днем растет число работников занятых в сфере услуг и распространении информации. Если символами прошлых  столетий былиферма и фабрика, то символ нынешнего XXI века – этоофис.С каждым днем растет число работников занятых в сфере услуг и распространении информации. Если символами прошлых столетий былиферма и фабрика, то символ нынешнего XXI века – этоофис.",
                rating: 4,
                photo: "http://placehold.it/250x200",
                employeeId: 1,
                services: [
                    {
                        name: "Optional short description. Optional short description",
                        price: 25
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 40
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 80
                    }
                ]
            },
            {
                name: "Menu 3",
                price: 150,
                description: "С каждым днем растет число работников занятых в сфере услуг и распространении информации. Если символами прошлых  столетий былиферма и фабрика, то символ нынешнего XXI века – этоофис.",
                rating: 4,
                photo: "http://placehold.it/250x200",
                employeeId: 2,
                services: [
                    {
                        name: "Optional short description. Optional short description",
                        price: 25
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 40
                    },
                    {
                        name: "Optional short description. Optional short description",
                        price: 400
                    }
                ]
            },];
        $scope.place = {
            photos: [
                {
                    src: "http://placehold.it/800x400",
                    caption: "Some caption"
                },
                {
                    src: "http://placehold.it/800x400",
                    caption: "Some caption"
                },
                {
                    src: "http://placehold.it/800x400",
                    caption: "Some caption"
                }]
        };

        $scope.activeEmployee = $scope.employees[0].id;

        $scope.getNumber = function (num) {
            var x = new Array();
            for (var i = 0; i < num; i++) {
                x.push(i + 1);
            }
            return x;
        };

        $scope.isActive = function (num) {
            if (num == 0) return "active";
            else return ""
        };

        $scope.isActiveEmployee = function (id) {
            return $scope.activeEmployee == id;
        };

        $scope.setActiveEmployee = function (id) {
            $scope.activeEmployee = id;
        };


        $scope.sortField = undefined;
        $scope.reverse = false;
        $scope.sortByEmployee = false;

        $scope.isSortUp = function (name) {
            return name == $scope.sortField && !$scope.reverse;
        };

        $scope.isSortDown = function (name) {
            return name == $scope.sortField && $scope.reverse;
        };

        $scope.sortMenus = function (sortName) {
            if (sortName == $scope.sortField) {
                $scope.reverse = !$scope.reverse;
            }
            else {
                $scope.sortField = sortName;
                $scope.reverse = false;
            }
        };

        $scope.switchEmployeeSort = function () {
            $scope.sortByEmployee = !$scope.sortByEmployee;
        };

        $scope.getSortByEmployees = function () {
            return sortByEmployee;
        };

        $scope.filterMenus = function (menu) {
            if ($scope.sortByEmployee) {
                return menu.employeeId == $scope.activeEmployee;
            }
            else return true;
        }
    }]);
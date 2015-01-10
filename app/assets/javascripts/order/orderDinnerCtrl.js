var OrderDinnerCtrl = function ($modal,$cacheFactory, $scope, $http, $timeout, $location, OrderService) {


    $scope.orderInfo = {
        count:0,
        orgPrice : 0,
        salePrice : 0,
        showCart : false,
        showList :true,
        showLogin : false,
        foods : [],
        userName: "",
        loginFlg : false,
        orgFlg : false
    };
    var localData = localStorage.getItem("orderInfo");
    if(localData){
        $scope.orderInfo =  JSON.parse(localData);
    }

    $scope.pageUrl = "/findFood";
    new PageService($scope, $http, $timeout);

    $scope.$watch('orderInfo', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            var objStr=JSON.stringify($scope.orderInfo);
            localStorage.setItem("orderInfo",objStr);
        }
    }, true);

    OrderDinnerCtrl.prototype.tologin = function () {
        $scope.orderInfo.orgFlg = $scope.orderInfo.showList;
        $scope.orderInfo.showCart = false;
        $scope.orderInfo.showList = false;
        $scope.orderInfo.showLogin = true;
    };

    OrderDinnerCtrl.prototype.login = function () {
        console.debug("login()");
        return OrderService.login($scope.user).then((function (data) {
            $scope.orderInfo.userName = data.data;
            $scope.orderInfo.loginFlg = true;
            $scope.orderInfo.showLogin = false;
            $scope.orderInfo.showCart = true;
            $scope.orderInfo.showList = false;
            if($scope.orderInfo.orgFlg){
                $scope.orderInfo.showCart = false;
                $scope.orderInfo.showList = true;
            }

        }), function (error) {
            $scope.orderInfo.loginFlg = false;
            $scope.orderInfo.userName = "";
            console.error("Unable to get activities: " + error);
            $scope.orderInfo.error = error;
        });
    };

    OrderDinnerCtrl.prototype.logout = function () {
        console.debug("logout()");
        $scope.orderInfo.userName = "";
        $scope.orderInfo.loginFlg = false;
    };



    OrderDinnerCtrl.prototype.addFood = function (food) {
        var foodTemp = {
            id: food.id,
            foodId: food.foodId,
            foodName: food.foodName,
            source: food.source,
            priceStr: food.priceStr,
            price: food.price,
            count: food.count,
            picture: food.picture
        };

        $scope.orderInfo.foods.push(foodTemp);
        $scope.orderInfo.count++;
        $scope.orderInfo.orgPrice += food.price;
        $scope.orderInfo.salePrice += food.price;
    };

    OrderDinnerCtrl.prototype.deleteFood = function (food) {
        if (confirm('您确实要把该商品移出购物车吗？')) {
            $scope.orderInfo.foods.splice(food.rowIndex, 1);
            $scope.orderInfo.count--;
            $scope.orderInfo.orgPrice -= food.price;
            $scope.orderInfo.salePrice -= food.price;
        }
    };

    OrderDinnerCtrl.prototype.clearFoods = function () {
        if (confirm('您确实要清空购物车吗？')) {
            $scope.orderInfo.count = 0;
            $scope.orderInfo.foods = [];
            $scope.orderInfo.orgPrice = 0;
            $scope.orderInfo.salePrice = 0;
        }
    };

    OrderDinnerCtrl.prototype.confirmOrder = function () {
        if(!$scope.orderInfo.loginFlg){
            $scope.orderInfo.showCart = false;
            $scope.orderInfo.showList = false;
            $scope.orderInfo.showLogin = true;
            $scope.orderInfo.orgFlg = false;
            return;
        }

        if (confirm('您确实要提交订单吗？')) {
            $scope.pager.list = $scope.orderInfo.foods;
            OrderService.add($scope.pager).then(function (data) {
                console.info("add to userInfo successfully : " + data);
            }, function (error) {
                console.error("update to userInfo error : " + error.data);
            });

            $scope.orderInfo.count = 0;
            $scope.orderInfo.foods = [];
            $scope.orderInfo.orgPrice = 0;
            $scope.orderInfo.salePrice = 0;
        }
    };

    OrderDinnerCtrl.prototype.showCart = function () {
        $scope.orderInfo.showCart = true;
        $scope.orderInfo.showList = false;
        $scope.orderInfo.showLogin = false;
    };

    OrderDinnerCtrl.prototype.hideCart = function () {
        $scope.orderInfo.showCart = false;
        $scope.orderInfo.showList = true;
        $scope.orderInfo.showLogin = false;
    };



    OrderDinnerCtrl.prototype.findResult = function () {
        console.debug("findResult()");
        return OrderService.findResult($scope.pager).then((function (data) {
            console.debug("Promise returned " + data.length + " banks");
            $scope.pager = data.data;
            $scope.orderInfo.results = data.data.list;
        }), function (error) {
            console.error("Unable to get activities: " + error);
            $scope.orderInfo.error = error;
        });
    };

};

controllersModule.controller('OrderDinnerCtrl', OrderDinnerCtrl);
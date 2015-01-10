var OrderDinnerCtrl = function ($modal, $scope, $http, $timeout, $location, OrderService) {
    $scope.count = 0;
    $scope.orgPrice = 0;
    $scope.salePrice = 0;
    $scope.showCart = false;
    $scope.showList = true;
    $scope.showLogin = false;

    $scope.foods = [];
    if(!$scope.userName){
        $scope.userName = "";
        $scope.loginFlg = false;
    }


    $scope.pageUrl = "/findFood";
    new PageService($scope, $http, $timeout);



    OrderDinnerCtrl.prototype.login = function () {
        console.debug("login()");
        return OrderService.login($scope.user).then((function (data) {
            $scope.userName = data.data;
            $scope.loginFlg = true;
            $scope.showCart = true;
            $scope.showList = false;
            $scope.showLogin = false;
        }), function (error) {
            $scope.loginFlg = false;
            $scope.userName = "";
            console.error("Unable to get activities: " + error);
            $scope.error = error;
        });
    };



    $scope.addFood = function (food) {
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

        $scope.foods.push(foodTemp);
        $scope.count++;
        $scope.orgPrice += food.price;
        $scope.salePrice += food.price;
    };

    $scope.deleteFood = function (food) {
        if (confirm('您确实要把该商品移出购物车吗？')) {
            $scope.foods.splice(food.rowIndex, 1);
            $scope.count--;
            $scope.orgPrice -= food.price;
            $scope.salePrice -= food.price;
        }
    };

    $scope.clearFoods = function () {
        if (confirm('您确实要清空购物车吗？')) {
            $scope.count = 0;
            $scope.foods = [];
            $scope.orgPrice = 0;
            $scope.salePrice = 0;
        }
    };

    $scope.confirmOrder = function () {
        if(!$scope.loginFlg){
            $scope.showCart = false;
            $scope.showList = false;
            $scope.showLogin = true;
            return;
        }

        if (confirm('您确实要提交订单吗？')) {

            $scope.pager.list = $scope.foods;
            OrderService.add($scope.pager).then(function (data) {
                console.info("add to userInfo successfully : " + data);
            }, function (error) {
                console.error("update to userInfo error : " + error.data);
            });

            $scope.count = 0;
            $scope.foods = [];
            $scope.orgPrice = 0;
            $scope.salePrice = 0;
        }
    };

    OrderDinnerCtrl.prototype.showCart = function () {
        $scope.showCart = true;
        $scope.showList = false;
    };

    OrderDinnerCtrl.prototype.hideCart = function () {
        $scope.showCart = false;
        $scope.showList = true;
    };



    OrderDinnerCtrl.prototype.findResult = function () {
        console.debug("findResult()");
        return OrderService.findResult($scope.pager).then((function (data) {
            console.debug("Promise returned " + data.length + " banks");
            $scope.pager = data.data;
            $scope.results = data.data.list;
        }), function (error) {
            console.error("Unable to get activities: " + error);
            $scope.error = error;
        });
    };

};

controllersModule.controller('OrderDinnerCtrl', OrderDinnerCtrl);
var OrderDinnerCtrl = function ($modal, $scope, $http, $timeout, $location, OrderService) {
    $scope.count = 0;
    $scope.orgPrice = 0;
    $scope.salePrice = 0;
    $scope.showCart = true;
    $scope.foods = [];

    $scope.pageUrl = "/findMenuInfo";
    new PageService($scope, $http, $timeout);

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
        if (confirm('您确实要提交订单吗？')) {
            $scope.pager.list = $scope.foods;
            OrderService.add($scope.pager).then(function (data) {
                console.info("add to userInfo successfully : " + data);
            }, function (error) {
                console.error("update to userInfo error : " + error.data);
            });
        }
    };

    OrderDinnerCtrl.prototype.showCart = function () {
        $scope.showCart = false;
    };

    OrderDinnerCtrl.prototype.hideCart = function () {
        $scope.showCart = true;
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
var OrderModalCtrl = function ($scope, food, $modalInstance, OrderService) {
    $scope.order = food;
    $scope.update = function () {
        OrderService.add($scope.order).then(function (data) {
            console.info("add to userInfo successfully : " + data);
        }, function (error) {
            console.error("update to userInfo error : " + error.data);
        });
        $modalInstance.dismiss("cancel");
    };

    $scope.cancel = function () {
        $modalInstance.dismiss("cancel");
    };
};

controllersModule.controller('OrderModalCtrl', OrderModalCtrl);
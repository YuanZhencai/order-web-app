var OrderCtrl = function($modal,$scope, $location,OrderService) {
    $scope.pager = {
        filter: {
            EQS_status: ''
        },
        index: 0,
        pageSize: 2,
        pageNum: 1
    };

    OrderCtrl.prototype.findResult = function() {
        console.debug("findResult()");
        return OrderService.findResult($scope.pager).then((function(data) {
            console.debug("Promise returned " + data.length + " banks");
            $scope.pager = data.data;
            $scope.results = data.data.list;
        }), function(error) {
            console.error("Unable to get activities: " + error);
            $scope.error = error;
        });
    };
    $scope.addRow = function (food) {
        var order = {
            userName:"",
            foodName:food.foodName,
            source:food.source,
            priceStr:food.priceStr
        };
        $scope.open(order);
    };

    $scope.open = function (food) {
        $modal.open({
            templateUrl: "OrderModalContent.html",
            controller: "OrderModalCtrl",
            resolve: {
                food: function(){
                    return food;
                }
            }
        });
    };
};

controllersModule.controller('OrderCtrl', OrderCtrl);

var OrderCtrl = function($modal,$scope,$http,$timeout,$location,OrderService) {
    $scope.columnDefs = [
        {field: 'userName', displayName: '用户名'},
        {field: 'foodName', displayName: '菜名'},
        {field: 'priceStr', displayName: '价格'},
        {field: 'source', displayName: '来源'},
        {field: 'locked', displayName: '操作', cellTemplate: '/assets/faces/cell/userCell.html'}
    ];
    $scope.isNotInit = true;
    $scope.pageUrl = "/findOrderInfo";
    new PageService($scope, $http, $timeout);
    $scope.editRow = function (row) {
        $scope.open(row);

    };
    $scope.deleteRow = function (row) {
        UserService.delete(row.entity).then(function (data) {
            console.info("delete to userInfo successfully : " + data);
            $scope.myData.splice(row.rowIndex,1);
        }, function (error) {
            console.error("delete to userInfo error : " + error.data);
        });
    };

    OrderCtrl.prototype.exportDailyTotal = function () {
        console.debug("findResult()");
        return OrderService.exportDailyTotal($scope.pager).then((function (data) {
            console.debug("Promise returned " + data.length + " banks");
        }), function (error) {
            console.error("Unable to get activities: " + error);
            $scope.error = error;
        });
    };

    OrderCtrl.prototype.exportDailyDetail = function () {
        console.debug("findResult()");
        return OrderService.exportDailyDetail($scope.pager).then((function (data) {
            console.debug("Promise returned " + data.length + " banks");
        }), function (error) {
            console.error("Unable to get activities: " + error);
            $scope.error = error;
        });
    };


    $scope.open = function (row) {
        $modal.open({
            templateUrl: "OrderModalContent.html",
            controller: "OrderModalCtrl",
            resolve: {
                row: function(){
                    return row.entity;
                }
            }
        });
    };
};
controllersModule.controller('OrderCtrl', OrderCtrl);

var OrderService = function ($http, $q) {
    console.debug("constructing OrderService");

    OrderService.headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    };

    OrderService.defaultConfig = {
        headers: OrderService.headers
    };

    OrderService.prototype.findResult = function (pager) {
        var deferred;
        console.debug("OrderService()");
        deferred = $q.defer();
        $http.post("/findMenuInfo", pager).then(function (data, status) {
            console.info("Successfully find OrderResult - status " + data);
            return deferred.resolve(data);
        }, function (data, status) {
            console.error("Failed to find OrderResult - status " + status);
            return deferred.reject(data);
        });
        return deferred.promise;
    };


    OrderService.prototype.add = function (pager) {
        var deferred;
        console.debug("orderService.add()");
        deferred = $q.defer();
        $http.post("/addOrderInfo", pager).then(function (data) {
            console.info("Successfully add order");
            return deferred.resolve(data);
        }, function (data, status) {
            console.error("Failed to add order" + status);
            return deferred.reject(data);
        });
        return deferred.promise;
    };

    OrderService.prototype.update = function (order) {
        var deferred;
        console.debug("orderService.add()");
        deferred = $q.defer();
        $http.post("/updateOrderInfo", order).then(function (data) {
            console.info("Successfully add order");
            return deferred.resolve(data);
        }, function (data, status) {
            console.error("Failed to add order" + status);
            return deferred.reject(data);
        });
        return deferred.promise;
    };

    OrderService.prototype.exportDailyOrderList = function (pager) {
        var deferred;
        console.debug("OrderService()");
        deferred = $q.defer();
        $http.post("/exportDailyOrderList", pager).then(function (data, status) {
            console.info("Successfully find OrderResult - status " + data);
            return deferred.resolve(data);
        }, function (data, status) {
            console.error("Failed to find OrderResult - status " + status);
            return deferred.reject(data);
        });
        return deferred.promise;
    };
};
servicesModule.service('OrderService', OrderService);


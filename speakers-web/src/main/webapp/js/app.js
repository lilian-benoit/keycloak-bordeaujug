var module = angular.module('speakers', []);

var auth = {};
var logout = function() {
    console.log('> LOGOUT');
    auth.loggedIn = false;
    auth.authz = null;
    window.location = auth.logoutUrl;
};


angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak('keycloak.json');
    auth.loggedIn = false;

    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        auth.loggedIn = true;
        auth.authz = keycloakAuth;
        module.factory('Auth', function() {
            return auth;
        });
        angular.bootstrap(document, ["speakers"]);
    }).error(function () {
            window.location.reload();
        });

});

module.controller('GlobalCtrl', function($scope, $http, Auth) {
    $scope.speakers = [];
    $scope.reloadData = function() {
        $http.get("http://localhost:8180/speakers-rest/rest/speakers").success(function(data) {
            $scope.speakers = angular.fromJson(data);
        });

    };
});

module.config(function($httpProvider) {
    $httpProvider.responseInterceptors.push('errorInterceptor');
    $httpProvider.interceptors.push('authInterceptor');
});

module.factory('errorInterceptor', function($q) {
    return function(promise) {
        return promise.then(function(response) {
            return response;
        }, function(response) {
            if (response.status == 401) {
                console.log('> session timeout?');
                logout();
            } else if (response.status == 403) {
                alert("Forbidden");
            } else if (response.status == 404) {
                alert("Not found");
            } else if (response.status) {
                if (response.data && response.data.errorMessage) {
                    alert(response.data.errorMessage);
                } else {
                    alert("Une erreur inattendu s'est produite.");
                }
            }
            return $q.reject(response);
        });
    };
});

module.factory('authInterceptor', function($q, Auth) {
    return {
        request: function (config) {
            var deferred = $q.defer();
            if (Auth.authz.token) {
                Auth.authz.updateToken(5).success(function() {
                    config.headers = config.headers || {};
                    config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                    deferred.resolve(config);
                }).error(function() {
                        deferred.reject('Echec pendant l actualisation du token (refresh token)');
                    });
            }
            return deferred.promise;
        }
    };
});





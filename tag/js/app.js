var syakuApp = angular.module('syakuApp', [
	'AppControllers',
	'ngRoute',
	'ngResource',
	'ngCookies'
]);

// config : 모듈 로드 에서 수행 해야 할 작업 을 등록 하기 위해 이 방법을 사용합니다.
syakuApp.config(['$routeProvider', '$httpProvider', '$locationProvider',
	function($routeProvider, $httpProvider, $locationProvider) {
		// route
		$routeProvider.

			when('/', {
				templateUrl : './partials/index.html',
				controller : 'IndexCtrl'
			}).

			when('/signin', {
				templateUrl : './partials/signin.html',
				controller : 'SigninCtrl'
			}).

			when('/mypage', {
				templateUrl : './partials/mypage.html',
				controller : 'MypageCtrl'
			}).

			otherwise({
				redirectTo : '/'
			});

			/*$locationProvider.html5Mode({
				enabled: true,
				requireBase: false
			});*/
			//$locationProvider.hashPrefix('!');

		$httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			return {

				'request' : function(config) {
					config.headers['X-Auth-Token'] = $rootScope.access_token;

					return config || $q.when(config);
				},

				'responseError' : function(rejection) {
					var data = rejection.data;

					$rootScope.alert(data.error, data.message);

					return $q.reject(rejection);
				}
			};
		});
	}
]);

syakuApp.run(['$rootScope', '$cookieStore', '$location',
	function($rootScope, $cookieStore, $location) {

		$rootScope.$on('$routeChangeSuccess', function() {
			$rootScope.alert.error = false;
			$rootScope.access_token = $cookieStore.get('access_token');
		});

		$rootScope.alert = function(error, message) {
			$rootScope.alert.error = error;
			$rootScope.alert.message = message;
		}

		$rootScope.fnAlertClose = function() {
			$rootScope.alert.error = false;
		}

		$rootScope.fnSignOut = function() {
			delete $rootScope.access_token;
			$cookieStore.remove('access_token');
			$location.path('/');
		}

		$rootScope.fnIsActive = function(route) {
        	return route === $location.path();
    	}


	}
]);

syakuApp.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});

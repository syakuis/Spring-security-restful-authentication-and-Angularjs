var appControllers = angular.module('AppControllers',[ ]);

appControllers.controller('IndexCtrl', ['$scope', '$route', '$location', '$http', 
	function($scope, $route, $location, $http) {
		var request = $http({
				method : 'GET',
				url : '/rest/',
				header : {'accpet' : 'application/json' }
			});

		request.success(function(data) {
		});

	}
]);

appControllers.controller('SigninCtrl', ['$rootScope', '$scope', '$route', '$http', '$cookieStore', '$location',
	function($rootScope, $scope, $route, $http, $cookieStore, $location) {

		$scope.error = $route.current.params.error;
		if ($scope.error) {
			$rootScope.alert(true, '로그인 하셔야 합니다.');
		}

		$scope.fnSignin = function(user) {
			if ($scope.form.$invalid) return;

			var request = $http({
				method : 'POST',
				url : '/rest/signin_proc',
				params : user
			});

			request.success(function(data) {
				var access_token = data.data.access_token;
				$cookieStore.put('access_token', access_token);
				$location.url('/');
			});
		};
	}
]);

appControllers.controller('MypageCtrl', ['$scope', '$route', '$location', '$http',
	function($scope, $route, $location, $http) {

		$scope.user = { };		

		var request = $http({
			method : 'GET',
			url : '/rest/mypage',
			header : {'accpet' : 'application/json' }
		});

		request.success(function(data) {
			$scope.user = data;
		});

		request.error(function() {
			$location.url('/signin?error=true');
		});

	}
]);

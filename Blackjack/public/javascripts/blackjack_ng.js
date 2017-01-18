var blackjackApp = angular.module('blackjackApp', ['ngSanitize']);
 
blackjackApp.directive('blackjackTable' , function () {
	return {
		templateUrl: 'html/blackjackTable.html',
	}; 
});

blackjackApp.directive('blackjackCards' , function () {
	return {
		templateUrl: 'html/blackjackCards.html',
		scope: {
			playeridx: '@playeridx',
		}
	}; 
});

blackjackApp.directive('blackjackPlayer' , function () {
	return {
		templateUrl: 'html/blackjackPlayer.html',
		scope: {
			playeridx: '@playeridx',
		}
	}; 
});


blackjackApp.controller('blackjackController', function ($scope, $http){
	this.$onInit = function() {
		alert("hihhkj");
		$http.get('/json').success(function(data) {
				alert("jjhjkkjkljkj");
				$scope.table = data;
   	   	});
	}
	$scope.load = function () {
 			$http.get('/json').success(function(data) {
 				$scope.table = data;
       	   });
	}
	
	$http.get('/json').success(function(res) {	
 		$scope.jsonCommand = function(jsonCommand) {
 			$http.get('/json/'+jsonCommand).success(function(data) {
 				$scope.table = data;
       	   }); 
 		}
       
 		$scope.jsonNewPlayer = function() {
 			var playername = $("#inputname").val();
 			$http.get('/newPlayer/'+playername).success(function(data) {
 				$scope.table = data;  
 			});
		}
     });   
 });
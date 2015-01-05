controller
  .controller 'navContentCtrl', ['$scope', '$rootScope', ($scope, $rootScope) ->
    $scope.userprofile = $rootScope.userprofile
  ]
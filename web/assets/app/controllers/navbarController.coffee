controller
  .controller 'navbarCtrl', ['$scope', ($scope, Weibos) ->
    $scope.query = ''
    $scope.weibos = Weibos
  ]
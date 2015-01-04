controller
  .controller 'ToastCtrl', ['$scope', '$mdToast', ($scope, $mdToast) ->
    $scope.closeToast = ->
      $mdToast.hide()
  ]
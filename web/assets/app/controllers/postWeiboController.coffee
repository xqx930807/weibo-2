controller.
  controller 'postWeiboCtrl', ['$scope', '$modalInstance', '$state', 'authService', 'weiboService', ($scope, $modalInstance, $state, authService, weiboService) ->
    $scope.content = ''
    $scope.postweibo = ->
      auth = authService.auth()
      if auth.uid == undefined
        $state.go 'auth'
      else
        weiboService.postNew auth, $scope.content, (data) ->
          $modalInstance.dismiss 'cancel'
  ]
controller
  .controller 'userInformationCtrl', ['$scope', '$state', 'userService', 'authService', 'Weibos', ($scope, $state, userService, authService, Weibos) ->
    $scope.uid = authService.auth().uid
    $scope.tid = $state.params.id
    $scope.weibos = Weibos
    $scope.userprofile = {}
    $scope.initInfo = () ->
      auth = authService.auth()
      if auth == undefined
        $state.go 'auth'
      else
        userService.userprofile auth, $scope.tid, (data) ->
          console.log data
          $scope.userprofile = data
          if data.followed
            $scope.userprofile.buttoninfo = '取消关注'
          else
            $scope.userprofile.buttoninfo = '关注他！'
          Weibos.list = data.weibos
  ]
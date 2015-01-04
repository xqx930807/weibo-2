controller
  .controller 'weiboCtrl', ['$scope', '$state', 'authService', 'weiboService', ($scope, $state, authService, weiboService) ->
      if authService.auth() == undefined
        $state.go 'auth'

      $scope.weibos = []
      $scope.initWeibo = () ->
        auth = authService.auth()
        if auth == undefined
          $state.go 'auth'
        else
          weiboService.fetch (err, data) ->
            if err
              alert '加载微博出错：' + err
            else
              $scope.weibos = data
    ]
controller
  .controller 'weiboCtrl', ['$scope', '$state', '$cookieStore', 'authService', 'weiboService', ($scope, $state, $cookieStore, authService, weiboService, Weibos) ->
      if authService.auth() == undefined
        $state.go 'auth'

      $scope.weibos = Weibos
      $scope.initWeibo = () ->
        auth = authService.auth()
        if auth == undefined
          $state.go 'auth'
        else
          auth = $cookieStore.get 'weibo.auth'
          weiboService.fetchTimeline auth, (err, data) ->
            if err
              alert '加载微博出错：' + err
            else
              $scope.weibos = data
    ]
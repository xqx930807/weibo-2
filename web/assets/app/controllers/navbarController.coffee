controller
  .controller 'navbarCtrl', ['$scope', '$cookieStore', '$state', 'authService', 'weiboService', 'Weibos', ($scope, $cookieStore, $state, authService, weiboService, Weibos) ->
    $scope.query = ''

    $scope.search = () ->
      console.log "before:" + Weibos
      if $scope.query != ''
        weiboService.search authService.auth(), $scope.query, (data) ->
          Weibos.list = data

    $scope.logout = () ->
      authService.logout authService.auth(), ->
        $cookieStore.remove 'weibo.auth'
        alert "注销成功！"
        $state.go 'auth'
  ]
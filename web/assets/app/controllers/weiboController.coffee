controller
  .controller 'weiboCtrl', ['$scope', '$rootScope', '$state', '$cookieStore', 'authService', 'weiboService', 'Weibos', ($scope, $rootScope, $state, $cookieStore, authService, weiboService, Weibos) ->
      if authService.auth() == undefined
        $state.go 'auth'

      $scope.uid = authService.auth().uid
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
              Weibos.list = data
              console.log Weibos
              console.log $scope.weibos
              console.log $scope.weibos.list

      $scope.postComment = (index) ->
        input = prompt("输入评论吧")
        if (input == '')
          alert '没有输入评论内容哦'
        else
          wid = $scope.weibos.list[index].id
          auth = $cookieStore.get 'weibo.auth'
          weiboService.postComment wid, auth, input, (data) ->
            arr = data.info.split '>'
            $scope.weibos.list[index].comments.push
              id : arr[0]
              uid : auth.uid
              wid : wid
              content : input
              createdAt : arr[1]
              deletedAt : null
              nickname : $rootScope.userprofile.nickname
              avatar : $rootScope.userprofile.avatar
            alert '评论成功啦~'

      $scope.deleteWeibo = (index) ->
        console.log 'delete weibo'

      $scope.deleteComment = (index) ->
        console.log 'delete comment'
    ]
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

    $scope.deleteComment = (index, pindex) ->
      result = confirm("确定要删除这条评论吗？")
      if result is true
        wid = $scope.weibos.list[pindex].id
        cid = $scope.weibos.list[pindex].comments[index].id
        auth = $cookieStore.get 'weibo.auth'
        weiboService.deleteComment wid, cid, auth, (data) ->
          $scope.weibos.list[pindex].comments.splice index, 1
  ]
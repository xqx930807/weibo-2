controller
  .controller 'navContentCtrl', ['$scope', '$rootScope', '$modal', '$state', 'authService', ($scope, $rootScope, $modal, $state, authService) ->
    $scope.userprofile = $rootScope.userprofile

    if $scope.userprofile.nickname == undefined
      auth = authService.auth()
      auth = authService.auth()
      if auth == undefined
        $state.go 'auth'
      else
        authService.profile auth, (err, data) ->
          if err
            $state.go 'auth'
          else
            $scope.userprofile = data
            $rootScope.userprofile = data

    $scope.updateInfo = (where) ->
      info = ''
      defaultVal = ''
      if (where == 'signature')
        info = '个性签名'
        defaultVal = $scope.userprofile.signature
      else if (where == 'location')
        info = '地点'
        defaultVal = $scope.userprofile.location
      else
        return

      input = prompt("输入你要更新的" + info, defaultVal)
      if input is undefined
        alert "修改失败"

    $scope.openAvatarUploadModal = ->
      modalInstance = $modal.open
        templateUrl : '/assets/app/views/uploadAvatar.html'
        controller : 'uploadModalCtrl'
        resolve :
          '$modalInstance' : ->
            return ->
              modalInstance
      modalInstance.result.then (response) ->
        console.log response
      , ->
        console.log 'upload avatar dismiss'

    $scope.openWeiboModal = ->
      modalInstance = $modal.open
        templateUrl : '/assets/app/views/postweibo.html'
        controller : 'postWeiboCtrl'
        resolve :
          '$modalInstance' : ->
            return ->
              modalInstance
      modalInstance.result.then (response) ->
        console.log response
      , ->
        console.log 'post weibo dismiss'
  ]
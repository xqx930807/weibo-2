controller
  .controller 'loginCtrl', ['$scope', '$state', '$cookieStore', '$mdToast', '$rootScope', 'authService', ($scope, $state, $cookieStore, $mdToast, $rootScope, authService) ->
    if authService.auth() != undefined
      $state.go 'index'

    $scope.user = {
      email : ''
      password : ''
      nickname : ''
      avatar : ''
      location : ''
      signature : ''
      createdat : ''
    }

    $scope.toastPosition =
      bottom : true
      top : false
      left : true
      right : true

    $scope.getToastPosition = ->
       Object
          .keys($scope.toastPosition)
          .filter (pos) ->
            $scope.toastPosition[pos]
          .join(' ')


    $scope.login = ->
      auth = {
        email : $scope.user.email
        password : $scope.user.password
      }
      authService.login auth, (content) ->
        if content.result is true
          content.info.avatar = $rootScope.rootUrl + content.info.avatar if content.info.avatar?
          $rootScope.userprofile = content.info
          auth =
            uid : content.info.id
            email : content.info.email
            token : content.info.token
          $cookieStore.remove 'weibo.auth' if $cookieStore.get('weibo.auth')?
          $cookieStore.put 'weibo.auth', auth

          $state.go 'index'
        else
          $scope.info = content.info
          #$scope.showInfoToast
          alert content.info

    $scope.showInfoToast = ->
      $mdToast.show
        controller : 'ToastCtrl'
        templateUrl : 'assets/app/views/login.toast.html'
        hideDelay : 3000
        position : $scope.getToastPosition()


  ]

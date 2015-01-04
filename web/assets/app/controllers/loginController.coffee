controller
  .controller 'loginCtrl', ['$scope', '$state', '$mdToast', 'authService', ($scope, $state, $mdToast, authService) ->
    if authService.auth() != undefined
      $state.go 'index'

    $scope.user = {
      username : ''
      password : ''
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
        username : $scope.user.username
        password : $scope.user.password
      }
      authService.login auth, (content) ->
        console.log content
        if content.result is true
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

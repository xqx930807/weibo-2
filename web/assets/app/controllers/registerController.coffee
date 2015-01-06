controller
  .controller 'registerCtrl', ['$scope', '$state', 'authService', ($scope, $state, authService) ->
    $scope.info =
      email : ""
      nickname : ""
      password : ""
      repeatPassword : ""

    $scope.register = ->
      if $scope.info.password.length < 6
        alert "密码长度过短"
      else if $scope.info.password != $scope.info.repeatPassword
        alert "两次输入的密码不一致"
      else
        authService.register $scope.info.email, $scope.info.nickname, $scope.info.password, ->
          $state.go 'auth'
  ]
app = angular.module 'weiboApp', [
  'ngRoute', 'ngResource', 'ui.router'
]

app.config [
  '$stateProvider', ($stateProvider) ->
    $stateProvider.state('index', {
      url : '/test.html'
      template: '<h1>My Contacts</h1>'
    })
]

app.controller 'MainCtrl', [
  '$scope', '$state', ($scope, $state) ->
    $scope.text = "test text"

    $scope.change = ->
      console.log 'click'
      $state.go('index')
]
app = angular.module 'weiboApp', [
  'ngRoute', 'ngCookies', 'ui.router', 'ngMaterial', 'weiboApp.controllers', 'weiboApp.services', 'weibbApp.directives'
]

.run [
  '$rootScope', '$state', '$stateParams', ($rootScope, $state, $stateParams) ->
    $rootScope.$state = $state
    $rootScope.$stateParams = $stateParams
]

.config ($httpProvider) ->
    $httpProvider.interceptors.push ($location, $q) ->
      {
        'response' : (response) ->
          response or $q.when response

        'responseError' : (rejection) ->
          if rejection.status is 401
            $rootScope.$state.go 'auth'
          $q.reject rejection
      }

.config [
  '$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) ->

    $stateProvider
      .state 'index', {
        url : '/',
        views : {
          'navtitle' :
            template : ''
          'content' :
            templateUrl : "assets/app/views/content.html"
            controller : 'weiboCtrl'
        }
      }

      .state 'auth', {
        url : '/users/login'
        views :
          'navtitle' :
            template : "<span>请登录</span>"
          'content' :
            templateUrl : "assets/app/views/login.html"
            controller : "loginCtrl"
      }
]

.run ($rootScope, $state, $cookieStore) ->
  $state.go('index')
  #$cookieStore.remove 'weibo.auth'
  $rootScope.$on '$stateChangeStart', (event, toState, toParams, fromState, fromParams) ->

root = window ? this

root.controller = angular.module 'weiboApp.controllers', []
root.service = angular.module 'weiboApp.services', []
root.directive = angular.module 'weibbApp.directives', []
app = angular.module 'weiboApp', [
  'ngRoute', 'ngCookies', 'ui.router', 'ui.bootstrap', 'ngMaterial', 'weiboApp.controllers', 'weiboApp.services', 'weibbApp.directives', 'weiboApp.factories'
]

.run [
  '$rootScope', '$state', '$stateParams', ($rootScope, $state, $stateParams) ->
    $rootScope.$state = $state
    $rootScope.$stateParams = $stateParams
    $rootScope.userprofile = {}
    $rootScope.rootUrl = "http://localhost:8080"
]

.config ($httpProvider) ->
    $httpProvider.interceptors.push ($location, $rootScope, $q) ->
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
          'navcontent' :
            templateUrl : 'assets/app/views/navcontent.index.html'
            controller : 'navContentCtrl'
          'navbar' :
            templateUrl : 'assets/app/views/navbar.index.html'
            controller : 'navbarCtrl'
          'content' :
            templateUrl : "assets/app/views/content.html"
            controller : 'weiboCtrl'
        }
      }

      .state 'auth', {
        url : '/users/login'
        views :
          'navcontent' :
            templateUrl : 'assets/app/views/navcontent.normal.html'
          'navbar' :
            template : "<span>请登录 >></span>"
          'content' :
            templateUrl : "assets/app/views/login.html"
            controller : "loginCtrl"
      }

      .state 'register', {
        url : '/users/register'
        views :
          'navcontent' :
            templateUrl : 'assets/app/views/navcontent.normal.html'
          'navbar' :
            template : "<span>注册 >></span>"
          'content' :
            templateUrl : "assets/app/views/register.html"
            controller : "registerCtrl"
      }

      .state 'users', {
        url : '/users/:id/information'
        views :
          'navcontent' :
            templateUrl : 'assets/app/views/navcontent.index.html'
            controller : 'navContentCtrl'
          'navbar' :
            templateUrl : 'assets/app/views/navbar.index.html'
            controller : 'navbarCtrl'
          'content' :
            templateUrl : "assets/app/views/userinformation.html"
            controller : 'userInformationCtrl'

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
root.factory = angular.module 'weiboApp.factories', []
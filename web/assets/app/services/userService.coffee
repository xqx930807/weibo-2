service
  .factory 'userService', ['$http', ($http) ->
    userprofile : (auth, uid, callback) ->
      $http.get("http://localhost:8080/users/#{auth.uid}/user/#{uid}?token=#{auth.token}")
        .success (data, status) ->
          console.log data
          callback data
        .error (data, status) ->
          console.log data
  ]
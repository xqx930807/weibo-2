service
  .factory 'authService', ['$http', '$cookieStore', ($http, $cookieStore) ->
    {
      login : (auth, callback) ->
        $http({
              url : 'http://localhost:8080/users/login'
              method : 'POST'
              params : {
                email : auth.email
                password : auth.password
              }
            })
            .success (data, status) ->
              if status is 200
                callback
                  result : true
                  info : data
              else
                console.log data
                callback
                  result : false
                  info : ""
            .error (data, status) ->
              console.log data
              console.log "error:" + status
              callback
                result : false
                info : data.info

      logout : (auth, callback) ->
        $http({
          url : "http://localhost:8080/users/#{auth.uid}/logout"
          method : 'POST'
          params : {
            token : auth.token
          }
        })
        .success (data, status) ->
          callback()
        .error (data, status) ->
          alert "注销失败"


      auth : ->
        $cookieStore.get 'weibo.auth'
    }
  ]
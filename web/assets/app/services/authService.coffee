service
  .factory 'authService', ['$http', '$cookieStore', ($http, $cookieStore) ->
    {
      login : (auth, callback) ->
        $http({
              url : 'http://localhost:8080/users/login'
              method : 'POST'
              params : {
                username : auth.username
                password : auth.password
              }
            })
            .success (data, status) ->
              if status is 200
                $cookieStore.remove 'weibo.auth' if $cookieStore.get('weibo.auth')?
                $cookieStore.put 'weibo.auth', data
                callback
                  result : true
                  info : ""
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

      logout : ->
        console.log 'logout'

      auth : ->
        $cookieStore.get 'weibo.auth'
    }
  ]
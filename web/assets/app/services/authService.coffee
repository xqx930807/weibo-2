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

      register : (email, nickname, password, callback) ->
        $http({
          url : "http://localhost:8080/users/register"
          method : 'POST'
          params : {
            email : email
            nickname : nickname
            password : password
          }
        })
        .success (data, status) ->
          callback()
        .error (data, status) ->
          if data.info != undefined
            alert "注册失败：" + data.info
          else
            alert "注册失败：未知错误"

      profile : (auth, callback) ->
        $http.get("http://localhost:8080/users/#{auth.uid}/profile?token=#{auth.token}")
          .success (data, status) ->
            callback null, data
          .error (data, status) ->
            callback data, null

      updateAvatar : () ->
        console.log 'update avatar'
    }
  ]
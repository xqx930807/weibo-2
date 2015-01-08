service
  .factory 'weiboService', ['$http', ($http)->
    {
      fetchTimeline : (auth, callback) ->
        $http.get("http://localhost:8080/users/#{auth.uid}/timeline?token=#{auth.token}")
          .success (data, status) ->
            callback null, data
          .error (data, status) ->
            console.log status + ":" + data
            callback data, null

      search : (auth, query, callback) ->
        console.log query
        $http.get("http://localhost:8080/users/#{auth.uid}/weibo/search?query=#{query}&token=#{auth.token}")
          .success (data, status) ->
            console.log data
            callback data
          .error (data, status) ->
            callback
      postNew : (auth, content, callback) ->
        $http({
          url : "http://localhost:8080/users/#{auth.uid}/weibo"
          method : 'POST'
          params :
            token : auth.token
            content : content
        })
        .success (data, status) ->
          callback data
        .error (data, status) ->
          callback data

      deleteWeibo : (wid, auth, callback) ->


      postComment : (wid, auth, content, callback) ->
        $http({
          url : "http://localhost:8080/users/#{auth.uid}/weibo/#{wid}/comment"
          method : 'POST'
          params :
            token : auth.token
            content : content
        })
        .success (data, status) ->
          callback data
        .error (data, status) ->
          console.log 'comment failed.'

      deleteComment : (wid, cid, auth, callback) ->
        $http({
          url : "http://localhost:8080/users/#{auth.uid}/weibo/#{wid}/comment/#{cid}"
          method : 'DELETE'
          params :
            token : auth.token
        })
        .success (data, status) ->
          callback data
        .error (data, status) ->
          callback data
    }
  ]
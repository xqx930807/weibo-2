service
  .factory 'weiboService', ['$http', ($http)->
    {
      fetchTimeline : (auth, callback) ->
        console.log "http://localhost:8080/users/#{auth.uid}/timeline?token=#{auth.token}"
        console.log auth
        $http.get("http://localhost:8080/users/#{auth.uid}/timeline?token=#{auth.token}")
          .success (data, status) ->
            console.log data
            callback null, data
          .error (data, status) ->
            console.log status + ":" + data
            callback data, null

      search : (query) ->
        console.log query
        #$http.get('http://localhost:8080/weibo/search?query=+query')
    }
  ]
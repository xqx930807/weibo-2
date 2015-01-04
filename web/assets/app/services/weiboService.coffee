service
  .factory 'weiboService', ['$http', ($http)->
    {
      fetch : (callback) ->
        $http.get('http://localhost:8080/weibo')
          .success (data, status) ->
            console.log data
            callback null, data
          .error (data, status) ->
            console.log status + ":" + data
            callback data, null

      search : (query) ->
        $http.get('http://localhost:8080/weibo/search?query=+query')
    }
  ]
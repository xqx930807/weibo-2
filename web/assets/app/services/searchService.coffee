service
  .factory 'searchService', ['$http', ($http)->
    {
      search : (query) ->
        # $http.get http://localhost:8080/weibo/search?query=+query
    }
]
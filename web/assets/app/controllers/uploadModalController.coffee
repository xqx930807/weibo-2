controller.
  controller 'uploadModalCtrl', ['$scope', '$modalInstance', ($scope, $modalInstance) ->
    $scope.files = []
    $scope.onFileSelect = ($files) ->
      $scope.files = $files

    $scope.upload = ->

  ]
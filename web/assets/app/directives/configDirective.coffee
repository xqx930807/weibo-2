directive
  .directive 'appName', [
    'configFactory', (configFactory) ->
      (scope, element, attributes) ->
        element.text configFactory.name
  ]
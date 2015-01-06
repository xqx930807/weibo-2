directive
  .directive 'appName', [
    'configFactory', (configFactory) ->
      (scope, element, attributes) ->
        element.text configFactory.name
  ]
  .directive 'appVersion', [
    'configFactory', (configFactory) ->
      (scope, element, attributes) ->
        element.text configFactory.version
  ]
  .directive 'appFedev', [
    'configFactory', (configFactory) ->
      (scope, element, attributes) ->
        element.text configFactory.fedev
  ]
  .directive 'appBedev', [
    'configFactory', (configFactory) ->
      (scope, element, attributes) ->
        element.text configFactory.bedev
  ]

directivesModule.directive "ngPageChanged", ->
  (scope, element, attrs) ->
    scope.$watch "pagingOptions", ((newVal, oldVal) ->
      scope.$eval attrs.ngPageChanged
      return
    ), true


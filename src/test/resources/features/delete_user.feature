Feature: Delete user using JSON body

  Scenario Outline: Delete user by sending JSON body
    Given I have delete user payload with ID "<userId>"
    When I send DELETE request with JSON body
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | userId | statusCode |
      |      2 |        204 |
      |      4 |        204 |

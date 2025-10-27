Feature: Get user details using parameters

  Scenario Outline: Get user details by user ID
    Given I have user ID "<userId>"
    When I send GET request to fetch user details by path param
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | userId | statusCode |
      |      2 |        200 |
      |     23 |        404 |

  Scenario Outline: Fetch user list using query parameter
    Given I have query parameter "<key>" with value "<value>"
    When I send GET request to fetch users with query param
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | key   | value | statusCode |
      | page  |     2 |        200 |
      | delay |     3 |        200 |

  Scenario Outline: Fetch list of all users
    When I send GET request to fetch all users
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | statusCode |
      |        200 |

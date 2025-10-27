Feature: Update existing user details

  Scenario Outline: Update user with given ID
    Given I have user ID "<userId>"
    And I have update payload with name "<name>" and job "<job>"
    When I send PUT request to update user
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | userId | name      | job             | statusCode |
      |      2 | Krishna   | QA Architect    |        200 |
      |      4 | Chaitanya | Automation Lead |        200 |

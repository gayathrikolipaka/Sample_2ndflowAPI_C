Feature: Check user credentials with missing password
  Scenario Outline: Attempt to authorize user without providing password
    Given I have user credentials payload with username "<username>" and missing password
    When I send POST request to authorize user
    Then I should get valid response with status code "<statusCode>"
    And the response should contain error message "<errorMessage>"
    Examples:
      | username      | statusCode | errorMessage                              |
      | testuser123   | 400        | Missing password                          |

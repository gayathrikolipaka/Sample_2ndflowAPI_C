Feature: Check user credentials with incorrect credentials
  Scenario Outline: Attempt to authorize user with invalid credentials
    Given I have user credentials with username "<username>" and password "<password>"
    When I send POST request to /Account/v1/Authorized endpoint
    Then I should get valid response with status code "<statusCode>"
    And the response should indicate unauthorized access
    Examples:
      | username      | password      | statusCode |
      | invalidUser   | wrongPass123  | 401        |
      | testuser      | badpassword   | 401        |

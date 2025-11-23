Feature: Authorize user with valid credentials containing special characters
  Scenario Outline: Authorize user with special character credentials
    Given I have user credentials with username "<username>" and password "<password>"
    When I send POST request to /Account/v1/Authorized endpoint
    Then I should get valid response with status code "<statusCode>"
    And the response body should indicate successful authorization
    Examples:
      | username           | password               | statusCode |
      | test.user!@#       | Passw0rd$%^&*()_+      | 200        |
      | john.doe+api@email.com | My$ecureP@ss!2024      | 200        |

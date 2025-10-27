Feature: Verify user authentication with valid credentials
  As a valid user
  I want to authenticate using valid credentials
  So that I can access protected resources

  Scenario Outline: Successfully authenticate with valid user credentials
    Given I have user login payload with username "<username>" and password "<password>"
    When I send POST request to authenticate user
    Then I should get valid response with status code "<statusCode>"
    And the response body should be an empty JSON object
    
    Examples:
      | username      | password    | statusCode |
      | valid.user01  | Passw0rd!   | 200        |

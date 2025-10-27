Feature: Verify user login with valid credentials
  As an API consumer
  I want to verify that the API authorizes valid user credentials
  So that authenticated users can access protected resources

  Scenario Outline: Successfully authorize user with valid credentials
    Given I have a valid user credentials payload with username "<username>" and password "<password>"
    When I send POST request to the login endpoint
    Then I should get valid response with status code "<statusCode>"
    And the response body should indicate successful authentication
    
    Examples:
      | username   | password   | statusCode |
      | testuser   | testpass   | 200        |

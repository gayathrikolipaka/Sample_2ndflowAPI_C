Feature: Validate user credentials API
  As a functional tester
  I want to verify that the API authorizes valid user credentials
  So that only valid users can access the system

  Scenario Outline: Successfully authorize user with valid credentials
    Given I have valid user credentials payload with username "<username>" and password "<password>"
    When I send POST request to authorize user
    Then I should get valid response with status code "<statusCode>"
    And the response body should contain a success message or be empty
    
    Examples:
      | username      | password    | statusCode |
      | validUser123  | Passw0rd!   | 200        |

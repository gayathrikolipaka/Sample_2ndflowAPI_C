Feature: Verify User Login API
  As a functional tester
  I want to verify that the API authorizes valid user credentials
  So that authenticated users can access the system

  Scenario Outline: Successfully authorize with valid user credentials
    Given I have valid user credentials with username "<username>" and password "<password>"
    When I send POST request to login endpoint
    Then I should get valid response with status code "<statusCode>"
    And the response body should indicate successful authentication

    Examples:
      | username    | password    | statusCode |
      | validUser01 | validPass01 | 200        |

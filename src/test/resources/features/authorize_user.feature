Feature: Authorize user with valid credentials
  Scenario Outline: Successfully authorize user with valid credentials
    Given I have user credentials with username "<username>" and password "<password>"
    When I send POST request to authorize user
    Then I should get valid response with status code "<statusCode>"
    And the response body should indicate authorization success
    Examples:
      | username      | password      | statusCode |
      | validUser123  | ValidPass!23  | 200        |

Feature: Handle Bad Request for Account Authorization API
  Scenario Outline: Send malformed request body to /Account/v1/Authorized and verify 400 Bad Request
    Given I have a malformed request payload for account authorization
    When I send POST request to /Account/v1/Authorized
    Then I should get valid response with status code "<statusCode>"
    And the response should contain error message indicating malformed request syntax
    Examples:
      | statusCode |
      | 400        |

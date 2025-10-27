Feature: Check user credentials with empty JSON body
  Scenario: Attempt to authorize user with empty JSON body
    Given I have an empty JSON payload for user authorization
    When I send POST request to /Account/v1/Authorized endpoint
    Then I should get valid response with status code "400"
    And the response should contain validation error message for missing 'userName' and 'password'

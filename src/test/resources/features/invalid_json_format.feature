Feature: Invalid JSON Format Handling
  As an API consumer
  I want the API to reject requests with invalid JSON
  So that the system remains robust and secure

  Scenario: Attempt to create a new user with invalid JSON format in the request body
    Given I have an invalid JSON payload for user creation
    When I send POST request to create user with invalid JSON
    Then I should get valid response with status code "400"
    And the response should indicate a bad request due to invalid JSON format

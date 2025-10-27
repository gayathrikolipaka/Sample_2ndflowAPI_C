Feature: Invalid JSON Format in Create User API
  As an API consumer
  I want to verify the API returns an error for invalid JSON format in the request body
  So that the API is robust against malformed input

  Scenario: Attempt to create a new user with invalid JSON format
    Given I have an invalid JSON payload for creating a user
    When I send POST request to create user with invalid JSON
    Then I should get valid response with status code "400"
    And the response should indicate a bad request due to invalid JSON format

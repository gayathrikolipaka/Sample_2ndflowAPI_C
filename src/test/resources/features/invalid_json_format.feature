Feature: Create user with invalid JSON format
  As an API consumer
  I want to ensure the API returns an error when invalid JSON is sent in the request body
  So that the API is robust against malformed input

  Scenario: Attempt to create a new user with invalid JSON format
    Given I have an invalid JSON payload for user creation
    When I send POST request to create user with invalid JSON
    Then I should get valid response with status code "400"
    And the response should indicate a bad request due to invalid JSON format

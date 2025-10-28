Feature: Invalid JSON Format for Create User API
  As an API consumer
  I want to receive an error when sending invalid JSON format in the request body
  So that the API validates input and returns appropriate error responses

  Scenario Outline: Attempt to create a new user with invalid JSON format
    Given I have an invalid JSON payload for user creation
    When I send POST request to create user
    Then I should get valid response with status code "<statusCode>"
    And the response should indicate an invalid JSON format error
    
    Examples:
      | statusCode |
      | 400        |

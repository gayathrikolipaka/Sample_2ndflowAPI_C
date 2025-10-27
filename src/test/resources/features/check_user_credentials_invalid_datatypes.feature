Feature: Check user credentials with invalid data types
  As an API consumer
  I want to ensure the system rejects invalid data types for user credentials
  So that only valid data is accepted for authentication

  Scenario Outline: Attempt to check user credentials with invalid data types
    Given I have user credentials payload with username <username> and password <password>
    When I send POST request to check user credentials
    Then I should get valid response with status code "400"
    And the response should contain error message indicating invalid data type

    Examples:
      | username      | password      |
      | 12345         | "password"   |
      | "username"    | 98765        |
      | true          | false        |
      | null          | "password"   |
      | "username"    | null         |

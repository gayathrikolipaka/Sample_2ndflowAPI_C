Feature: Create User API

  # Scenario 1 - Create multiple users successfully using Example table
  Scenario Outline: Successfully create a user with valid details
    Given I have user details payload 
    When I send POST request to create user with name "<name>" and job "<job>"
    Then I should get valid response with status code "<statusCode>"

    Examples: 
      | name      | job             | statusCode |
      | Chaitanya | Test Architect  |        201 |
      #| Krishna   | QA Lead         |        201 |
      #| Anjali    | Automation Engr |        201 |


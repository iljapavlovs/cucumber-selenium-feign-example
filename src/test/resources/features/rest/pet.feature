@rest
Feature: REST - Pet API

  Scenario: REST - Create pet

    When REST - Create pet
      | name   | status    |
      | Sharik | available |
    Then Response - status code is 200
@rest
@mock
Feature: MOCK REST - Inventory API

  Scenario: MOCK REST - Get Store Inventory - Status Code
    Given MOCK - mock GET request on "/pet"
    When REST - Get /pet
    Then MOCK - wait until mock is triggered for "/pet" path
#    Then Response - status code is 200
@rest
Feature: REST - Inventory API

  Scenario: REST - Get Store Inventory
    When REST - Get store inventory
    Then Response - status code is 200
    And Response - body is
    """
    {"Shortlisted":1,"Sold":1,"string":593,"1.1":1,"Hello":1,"pending":5,"available":971,"Unavailable":1,"status":2}
    """

  Scenario: REST - Get Store Inventory - Status Code
    When REST - Get store inventory
    Then Response - status code is 200




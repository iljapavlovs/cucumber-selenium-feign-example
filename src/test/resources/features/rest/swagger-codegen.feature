# BEFORE RUNNING THIS TEST:
#Generate HTTP client for http://petstore.swagger.io/v2/ resource with swagger-codegen tool by running:
# mvn clean compile

@rest
Feature: SWAGGER-CODEGEN REST - Inventory API

  Scenario: REST - Get Store Inventory - Status Code
    When SWAGGER-CODEGEN REST - Get store inventory
    Then SWAGGER-CODEGEN Response - status code is 200
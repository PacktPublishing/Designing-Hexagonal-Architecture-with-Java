@NetworkCreate
Feature: Can I create new networks?

  Scenario: Creating a new network
    Given I provide all required data to create a network
    Then A new network is created
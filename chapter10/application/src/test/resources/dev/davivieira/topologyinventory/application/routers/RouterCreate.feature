@RouterCreate
Feature: Can I create a new routers?

  Scenario: Creating a new core router
    Given I provide all required data to create a core router
    Then A new core router is created

  Scenario: Creating a new edge router
    Given I provide all required data to create an edge router
    Then A new edge router is created
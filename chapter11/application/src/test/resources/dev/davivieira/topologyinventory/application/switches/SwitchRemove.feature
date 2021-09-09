@SwitchRemove
Feature: Can I remove a switch from an edge router?

  Scenario: Removing a switch from an edge router
    Given I know the switch I want to remove
    And The switch has no networks
    Then I remove the switch from the edge router
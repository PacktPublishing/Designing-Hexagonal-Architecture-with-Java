@RouterRemove
Feature: Can I remove routers?

  #Scenario: Removing an edge router from a core router
  #  Given The core router has at least one edge router connected to it
  #  And The switch has no networks attached to it
  #  And The edge router has no switches attached to it
  #  Then I remove the edge router from the core router

  Scenario: Removing a core router from another core router
    Given The core router has at least one core router connected to it
    And The core router has no other routers connected to it
    Then I remove the core router from another core router
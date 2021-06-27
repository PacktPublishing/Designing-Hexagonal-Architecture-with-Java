@addNetworkToRouter
Feature: Add network to a router
  I want to be able to add a network to an existent router

  Scenario: Adding a network to an existent router
    Given I provide a router ID and the network details
    When I found the router
    And The network address is valid and doesn't already exists
    And The CIDR is valid
    Then Add the network to the router
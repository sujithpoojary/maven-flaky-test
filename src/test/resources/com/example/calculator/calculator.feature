Feature: Calculator basic operations
  Scenario: Add two numbers
    Given I have a calculator
    When I add 2 and 3
    Then the result should be 5

  Scenario: Divide by non-zero
    Given I have a calculator
    When I divide 10 by 2
    Then the result should be 5

  Scenario: Division by zero should error
    Given I have a calculator
    When I divide 10 by 0
    Then a division error should occur

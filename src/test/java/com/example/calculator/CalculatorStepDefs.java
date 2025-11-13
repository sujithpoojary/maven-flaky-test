package com.example.calculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class CalculatorStepDefs {
    private Calculator calculator;
    private int result;
    private Exception captured;

    @Given("I have a calculator")
    public void i_have_a_calculator() {
        System.out.println("[STEP] Given I have a calculator");
        calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void i_add_and(Integer a, Integer b) {
        System.out.println("[STEP] When I add " + a + " and " + b);
        result = calculator.add(a, b);
    }

    @Then("the result should be {int}")
    public void the_result_should_be(Integer expected) {
        System.out.println("[STEP] Then the result should be " + expected + " (actual=" + result + ")");
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @When("I divide {int} by {int}")
    public void i_divide_by(Integer a, Integer b) {
        System.out.println("[STEP] When I divide " + a + " by " + b);
        try {
            result = calculator.divide(a, b);
        } catch (Exception e) {
            captured = e;
        }
    }

    @Then("a division error should occur")
    public void a_division_error_should_occur() {
        System.out.println("[STEP] Then a division error should occur (captured=" + (captured!=null?captured.getMessage():"none") + ")");
        Assertions.assertThat(captured).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Division by zero");
    }
}

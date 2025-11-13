package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demo flaky test to showcase Jenkins Flaky Test Handler plugin.
 * The first run of the test method "failsOnceThenPasses" will fail (static flag), causing Surefire to rerun it.
 * On rerun it will pass, allowing the plugin to classify it as flaky.
 * Another test "randomFlaky" fails randomly (40% chance) to provide additional noisy flakiness.
 */
public class FlakyDemoTest {

    private static final AtomicBoolean firstRun = new AtomicBoolean(true);

    @Test
    @EnabledIfSystemProperty(named = "enable.flaky.demo", matches = "true")
    @DisplayName("Fails first time, passes subsequently to simulate flakiness")
    void failsOnceThenPasses() {
        boolean pass = !firstRun.getAndSet(false); // first invocation returns false -> fail, later true
        assertThat(pass).as("Should pass after initial rerun").isTrue();
    }

    @Test
    @EnabledIfSystemProperty(named = "enable.flaky.demo", matches = "true")
    @DisplayName("Randomly fails to simulate intermittent instability")
    void randomFlaky() {
        boolean pass = Math.random() > 0.4d; // 60% pass probability
        assertThat(pass).as("Simulated random flaky assertion").isTrue();
    }
}

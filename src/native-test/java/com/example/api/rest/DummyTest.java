package com.example.api.rest;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// We don't test native methods currently. One Test has to be available for Quarkus.
@QuarkusIntegrationTest
class DummyTest {
    @Test
    void dummyTest() {
        assertTrue(true);
    }
}

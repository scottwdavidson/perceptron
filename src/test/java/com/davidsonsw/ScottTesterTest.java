package com.davidsonsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScottTesterTest {

    private ScottTester scottTester;

    @BeforeEach
    void setUp() {
        scottTester = new ScottTester();
    }

    @Test
    void getFullName() {
        scottTester.setFirstName("Linda");
        scottTester.setLastName("Davidson");
        assertEquals("DAVIDSON, LINDA", scottTester.getFullName());
    }

    @Test
    void getFirstName() {
        scottTester.setFirstName("scott");
        assertEquals("scott", scottTester.getFirstName());
    }

    @Test
    void getLastName() {
        scottTester.setLastName("davidson");
        assertEquals("davidson", scottTester.getLastName());
    }
}
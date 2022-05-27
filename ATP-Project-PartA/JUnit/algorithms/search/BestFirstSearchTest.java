package algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    BestFirstSearch bestFirstSearchForTest = new BestFirstSearch();

    @Test
    void getName() {
        assertEquals("BestFirstSearch", bestFirstSearchForTest.getName());
    }
}
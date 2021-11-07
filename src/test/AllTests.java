package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Test suite including all JUnit tests for the model, for convenience.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardTest.class,
        DiceRollerTest.class,
        EmptySpaceTest.class,
        PlayerTest.class,
        PropertySpaceTest.class,
        PropertyTest.class,
        TurnOrderTest.class
})
public class AllTests {
}

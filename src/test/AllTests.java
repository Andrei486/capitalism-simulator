package test;

import main.Railroad;
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
        GoSpaceTest.class,
        GoToJailSpaceTest.class,
        JailSpaceTest.class,
        PlayerTest.class,
        PropertySpaceTest.class,
        RailroadTest.class,
        RealEstateGroupTest.class,
        RealEstateTest.class,
        TurnOrderTest.class,
        UtilityTest.class,
})
public class AllTests {
}

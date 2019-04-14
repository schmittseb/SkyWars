package com.skywars.Test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
    public AllTests() {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        suite.addTestSuite(BattleShooterTest.class);
        suite.addTestSuite(BattleStarTest.class);
        suite.addTestSuite(BoardTest.class);
        suite.addTestSuite(MasterShipTest.class);
        suite.addTestSuite(MoveTest.class);
        suite.addTestSuite(ShipTest.class);
        suite.addTestSuite(TileTest.class);
        suite.addTestSuite(TransitionTest.class);
        return suite;
    }
}

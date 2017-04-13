package warlordstest;

import App.Game.Fort.FortComponent;
import App.Game.GameComponent;
import App.Shared.SharedModule;
import junit.framework.TestSuite;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class WarlordsTest extends TestSuite {

    private IGame game;
    private IBall ball;
    private IPaddle paddle;
    private IWall player1Wall;
    private IWarlord player1;
    private IWarlord player2;

    @Before
    public void setUp(){
        //Instantiate objects to initialise the fields - and preferably no other game objects, to minimise the possibility of conflicts
        //All game objects should be instantiated at coordinates (0,0) with zero velocity
        SharedModule sharedModule = new SharedModule();

        GameComponent game = new GameComponent(new SharedModule());
        this.game = game;
        game.load();
        this.ball = game.getBall();

        FortComponent player1Fort = game.addPlayer(1, 1, new Point.Double(0, 0));
        FortComponent player2Fort = game.addPlayer(2, 1, new Point.Double(736, 445));

        this.paddle = player1Fort.getShield();
        this.player1Wall = player1Fort.getWalls().get(0);
        this.player1 = player1Fort.getWarlord();
        this.player2 = player2Fort.getWarlord();
    }

    @Test
    public void testBallMovement(){
        this.ball.setXPos(500);
        this.ball.setYPos(500);

        this.ball.setXVelocity(50);
        this.ball.setYVelocity(50);

        this.game.tick();

        assertTrue("If unimpeded, the ball should be moved by its velocity in each direction", this.ball.getXPos() == 550 && this.ball.getYPos() ==  550);
    }

    @Test
    public void testBallCollisionWithBoundary(){

        this.ball.setXPos(10);
        this.ball.setYPos(500);

        this.ball.setXVelocity(-50);
        this.ball.setYVelocity(50);

        this.game.tick();

        assertTrue("The ball should remain within bounds", this.ball.getXPos() >= 0);
        assertTrue("The ball's velocity should be reversed in the direction of the collision", this.ball.getXVelocity() == 50 && this.ball.getYVelocity() == 50);


    }

    @Test
    public void testBallCollisionWithPaddle(){

        this.ball.setXPos(500);
        this.ball.setYPos(495);
        this.ball.setXVelocity(10);
        this.ball.setYVelocity(10);

        this.paddle.setXPos(500);
        this.paddle.setYPos(500);

        this.game.tick();

        assertTrue("The ball should not travel through the paddle", this.ball.getYPos() <= 500);
        assertTrue("The ball's velocity should be reversed in the direction of the collision", this.ball.getXVelocity() == 10 && this.ball.getYVelocity() == -10);

    }

    @Test
    public void testBallCollisionWithWall(){

        this.ball.setXPos(500);
        this.ball.setYPos(495);
        this.ball.setXVelocity(10);
        this.ball.setYVelocity(10);

        this.player1Wall.setXPos(500);
        this.player1Wall.setYPos(500);

        assertFalse("The wall should not be destroyed yet", this.player1Wall.isDestroyed());

        this.game.tick();

        assertTrue("The ball should not travel through the wall", this.ball.getYPos() <= 500);
        assertTrue("The ball's velocity should be reversed in the direction of the collision", this.ball.getXVelocity() == 10 && this.ball.getYVelocity() == -10);
        assertTrue("The wall should be destroyed", this.player1Wall.isDestroyed());

    }

    @Test
    public void testBallCollisionWithWarlord(){

        this.ball.setXPos(500);
        this.ball.setYPos(495);
        this.ball.setXVelocity(10);
        this.ball.setYVelocity(10);

        this.player1.setXPos(500);
        this.player1.setYPos(500);

        assertFalse("The warlord should not be dead yet", this.player1.isDead());

        this.game.tick();

        assertTrue("The warlord should be dead", this.player1.isDead());

    }

    @Test
    public void testBallCollisionAtHighSpeed(){

        this.ball.setXPos(500);
        this.ball.setYPos(495);
        this.ball.setXVelocity(300);
        this.ball.setYVelocity(300);

        this.paddle.setXPos(500);
        this.paddle.setYPos(500);

        this.game.tick();

        assertTrue("The ball should not travel through the paddle, even though it isn't within its bounds on any frame", this.ball.getYPos() <= 500);
        assertTrue("The ball's velocity should be reversed in the direction of the collision", this.ball.getXVelocity() == 300 && this.ball.getYVelocity() == -300);

    }

    @Test
    public void testGameEndFromKnockout(){

        assertFalse("The game should not be finished yet", this.game.isFinished());
        assertFalse("Fort 2 should not have won yet", this.player2.hasWon());

        this.ball.setXPos(500);
        this.ball.setYPos(495);
        this.ball.setXVelocity(10);
        this.ball.setYVelocity(10);

        this.player1.setXPos(500);
        this.player1.setYPos(500);

        this.game.tick();

        assertTrue("The game should be finished", this.game.isFinished());
        assertTrue("Fort 2 should have won", this.player2.hasWon());

    }

    @Test
    public void testGameEndFromTimeout(){

        assertFalse("The game should not be finished yet", this.game.isFinished());
        assertFalse("Fort 1 should not have won yet", this.player1.hasWon());

        this.game.setTimeRemaining(0);

        this.game.tick();

        assertTrue("The game should be finished", this.game.isFinished());
        assertTrue("Fort 1 should have won", this.player1.hasWon());

    }



}

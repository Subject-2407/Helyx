import org.junit.jupiter.api.Test;

import com.steven.helyx.characters.Player;
import com.steven.helyx.game.PVP;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testGetName() {
        Player player = new Player("Steven", "Novice");
        assertEquals("Steven", player.getName());
    }

    @Test
    void testCurrentHP() {
        Player player = new Player("Steven", "Novice");
        assertEquals(100, player.getCurrentHP());
    }

    @Test
    void testSetHP() {
        Player player = new Player("Steven", "Novice");
        player.setHP(77);
        assertEquals(77, player.getCurrentHP());
    }

    @Test
    void testSetEnergy() {
        Player player = new Player("Steven", "Novice");
        player.setEnergy(5);
        assertEquals(5, player.getEnergy());
    }

    @Test
    void testAttack() {
        Player player = new Player("Steven", "Novice");
        int damage = player.attack();
        assertTrue(damage > 0, "Attack damage should be greater than 0");
    }

    @Test
    void testTakeDamage() {
        Player player = new Player("Steven", "Novice");
        int damage = player.takeDamage(54);
        assertTrue(damage > 0, "Damage received should be greater than 0");
    }
}

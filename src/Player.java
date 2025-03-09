/**
 * The Player class represents a player in the Jungle King game.
 * Each player has a name.
 */
public class Player {
    private String name;


    /**
     * Constructs a new Player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
    }


    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName(){
        return this.name;
    }
}

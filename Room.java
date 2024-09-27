import java.util.HashMap;

public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        if (north != null) exits.put("north", north);
        if (east != null) exits.put("east", east);
        if (south != null) exits.put("south", south);
        if (west != null) exits.put("west", west);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder returnString = new StringBuilder();
        for (String direction : exits.keySet()) {
            returnString.append(direction).append(" ");
        }
        return returnString.toString();
    }

    public String getDescription() {
        return description;
    }
}
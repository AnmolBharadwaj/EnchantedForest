import java.util.Scanner;
import java.util.Random;

public class Game {
    private Player player;
    private Room currentRoom;
    private Scanner scanner;
    private Random random;

    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();
        player = new Player("Adventurer", 100, 10);
        initializeRooms();
    }

    private void initializeRooms() {
        Room entrance = new Room("Forest Entrance", "You stand at the entrance of a mysterious, enchanted forest.");
        Room clearing = new Room("Sunlit Clearing", "A peaceful clearing bathed in soft sunlight.");
        Room cave = new Room("Dark Cave", "A foreboding cave mouth leads into darkness.");
        Room grove = new Room("Ancient Grove", "An ancient grove with trees older than time itself.");

        entrance.setExits(clearing, cave, grove, null);
        clearing.setExits(null, entrance, null, grove);
        cave.setExits(entrance, null, null, null);
        grove.setExits(null, null, clearing, entrance);

        currentRoom = entrance;
    }

    public void start() {
        System.out.println("Welcome to The Enchanted Forest!");
        System.out.println("You find yourself on a quest to discover the heart of the forest.");
        System.out.println("Type 'help' for a list of commands.");

        boolean gameRunning = true;
        while (gameRunning) {
            System.out.println("\n" + currentRoom.getDescription());
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "north":
                case "south":
                case "east":
                case "west":
                    move(command);
                    break;
                case "look":
                    look();
                    break;
                case "status":
                    status();
                    break;
                case "help":
                    help();
                    break;
                case "quit":
                    gameRunning = false;
                    System.out.println("Thank you for playing The Enchanted Forest. Goodbye!");
                    break;
                default:
                    System.out.println("I don't understand that command. Type 'help' for a list of commands.");
            }

            if (random.nextDouble() < 0.2) { // 20% chance of encounter
                encounter();
            }
        }

        scanner.close();
    }

    private void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("You can't go that way.");
        } else {
            currentRoom = nextRoom;
            System.out.println("You move " + direction + ".");
        }
    }

    private void look() {
        System.out.println(currentRoom.getDescription());
        System.out.println("Exits: " + currentRoom.getExitString());
    }

    private void status() {
        System.out.println(player.getStatus());
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("  north, south, east, west - Move in a direction");
        System.out.println("  look - Look around the current room");
        System.out.println("  status - Check your health and attack power");
        System.out.println("  help - Show this help message");
        System.out.println("  quit - Exit the game");
    }

    private void encounter() {
        System.out.println("\nYou've encountered a forest creature!");
        int creatureHealth = random.nextInt(50) + 20;
        int creatureAttack = random.nextInt(10) + 5;

        while (creatureHealth > 0 && player.getHealth() > 0) {
            System.out.println("\nCreature Health: " + creatureHealth);
            System.out.println("Your Health: " + player.getHealth());
            System.out.println("Do you want to (a)ttack or (r)un?");
            String action = scanner.nextLine().toLowerCase();

            if (action.equals("a")) {
                int damage = player.attack();
                creatureHealth -= damage;
                System.out.println("You deal " + damage + " damage to the creature.");

                if (creatureHealth > 0) {
                    int creatureDamage = random.nextInt(creatureAttack);
                    player.takeDamage(creatureDamage);
                    System.out.println("The creature attacks you for " + creatureDamage + " damage.");
                }
            } else if (action.equals("r")) {
                if (random.nextDouble() < 0.5) {
                    System.out.println("You successfully escape from the creature!");
                    return;
                } else {
                    System.out.println("You failed to escape. The creature attacks!");
                    int creatureDamage = random.nextInt(creatureAttack);
                    player.takeDamage(creatureDamage);
                    System.out.println("The creature attacks you for " + creatureDamage + " damage.");
                }
            } else {
                System.out.println("Invalid action. The creature attacks!");
                int creatureDamage = random.nextInt(creatureAttack);
                player.takeDamage(creatureDamage);
                System.out.println("The creature attacks you for " + creatureDamage + " damage.");
            }
        }

        if (player.getHealth() <= 0) {
            System.out.println("You have been defeated. Game Over.");
            System.exit(0);
        } else {
            System.out.println("You have defeated the creature!");
            player.increaseAttack(1);
            System.out.println("Your attack power has increased!");
        }
    }
}
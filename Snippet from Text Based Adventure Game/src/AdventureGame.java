
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map;

public class AdventureGame {
    private static HashMap<String, Location> locations = new HashMap<>();
    private static HashMap<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {
        // Create locations and add them to the HashMap
        locations.put("forest", new Location("You are in a dense forest. There is a path to the north and east.", "apple"));
        locations.put("field", new Location("You are in a wide field. There is a path to the west and south.", "gold coin"));
        locations.put("mountain", new Location("You are at the base of a large mountain. There is a path to the north.", "rock"));
        locations.put("dungeon", new Location("You are in a dungeon. There is an exit on the other end.", "diamond"));

        // Connect the locations with each other
        locations.get("forest").addExit("north", "mountain");
        locations.get("forest").addExit("east", "field");
        locations.get("field").addExit("west", "forest");
        locations.get("field").addExit("south", "dungeon");
        locations.get("mountain").addExit("north", "dungeon");
        locations.get("dungeon").addExit("exit", "field");

        // Start the game in the forest
        String currentLocation = "forest";
        HashSet<String> foundLoot = new HashSet<>();
        boolean visitedAllLocations = false;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(locations.get(currentLocation).getDescription());
            System.out.print("Available exits: ");
            locations.get(currentLocation).getExits().forEach((direction, location) -> System.out.print(direction + " "));
            System.out.println();

            if (locations.get(currentLocation).getLoot() != null && (!foundLoot.contains(currentLocation) || (visitedAllLocations && foundLoot.contains(currentLocation)))) {
                String loot = locations.get(currentLocation).getLoot();
                System.out.println("You found: " + loot);
                if (inventory.containsKey(loot)) {
                    inventory.put(loot, inventory.get(loot) + 1);
                } else {
                    inventory.put(loot, 1);
                }
                foundLoot.add(currentLocation);
            }

            System.out.println("Inventory: ");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            String input = scanner.nextLine();
            if (input.equals("End Game")) {
                break;
            } else if (locations.get(currentLocation).getExits().containsKey(input)) {
                currentLocation = locations.get(currentLocation).getExits().get(input);
                if (foundLoot.size() == locations.size()) {
                    visitedAllLocations = true;
                }
            } else {
                System.out.println("Invalid direction.");
            }
        }
    }
    static class Location {
        private String description;
        private HashMap<String, String> exits;
        private String loot;

        public Location(String description, String loot) {
            this.description = description;
            this.exits = new HashMap<>();
            this.loot = loot;
        }

        public String getDescription() {
            return description;
        }

        public HashMap<String, String> getExits() {
            return exits;
        }

        public void addExit(String direction, String location) {
            exits.put(direction, location);
        }

        public String getLoot() {
            return loot;
        }

        public void setLoot(String loot) {
            this.loot = loot;
        }

    }
}
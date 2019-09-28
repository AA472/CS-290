import java.util.HashMap;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        String location;
        location = "Home";
        HashMap<String, Room> rooms = new HashMap<>();
        HashMap<String, Item> inventory = new HashMap<>();
        Readfile r = new Readfile();
        r.read(rooms);
        Scanner scan = new Scanner(System.in);
        Game game = new Game();
        System.out.println("Welcome to the dungeon!");
        System.out.println("You are going to start at home and will be given options as you go through the game.");
        System.out.println("You have an inventory with items. To check your inventory, type 'inventory'");
        System.out.println("To check for items at your location, type 'look' ");
        System.out.println("You can pick up an item by typing 'get' and the item's name");
        System.out.println("You can also examine your items to get more details by typing 'examine' and the items' name");
        System.out.println("You can move around the dungeon using 'go ' and  'n' or 'e' or 's' or 'w' for directions");
        System.out.println("Good luck!");
        System.out.println();
        while(true){
            System.out.println("Current location: " + location);
            rooms.get(location).describeRoom();
            if (location.equals("Forest2"))
                break;
            rooms.get(location).setVisited(true);
            if(location.equals("Neighborhood") ){
                if (!inventory.containsKey("Message"))
                    System.out.println("You don't have the message with you! You don't have time to go back and get it.");
                else if(inventory.containsKey("Food"))
                    System.out.println("You failed to deliver the message on time because you went to eat");
                else if(inventory.containsKey("LostMoney"))
                    System.out.println("You failed. The owner of the bag that you stole " +
                            "noticed you had it. You are going to jail.");
                else if (!(inventory.containsKey("Horse")||inventory.containsKey("MyHorse")))
                    System.out.println("It took you forever to walk here. You are too late.");
                else if ((inventory.containsKey("Horse")||inventory.containsKey("MyHorse")))
                    System.out.println("Good job! You delivered the message in time!");
                break;
            }
            String s = scan.nextLine();
            s = s.toLowerCase();
            String[] splited = s.split(" ");

            if(splited[0].equals("go"))
                location = game.go(splited[1],location,rooms);

            else if(splited[0].equals("pick") || s.equals("get") || s.equals("take"))
                game.pick(splited[1],inventory, rooms.get(location));

            else if(splited[0].equals("examine"))
                game.examine(inventory,splited[1]);

            else if(splited[0].equals("drop"))
                game.drop( splited[1],inventory, rooms.get(location));

            else if(splited[0].equals("use"))
                game.use(inventory, splited[1], rooms.get(location),  location);

            else if(splited[0].equals("look"))
                rooms.get(location).look();

            else if(splited[0].equals("inventory"))
                System.out.println("Inventory: " + inventory);

            else
                System.out.println("Not sure what you mean");

        }
    }
}


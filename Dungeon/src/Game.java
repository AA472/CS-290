import java.util.HashMap;

public class Game {

    public String go(String s, String location, HashMap<String, Room> rooms){
        String temp = location;

        if (s.equals("north") || s.equals("n")) {
            location = rooms.get(location).getnRoom().getRoomName();
        }
        else if (s.equals("east") || s.equals("e")) {
            location = rooms.get(location).geteRoom().getRoomName();
        }
        else if (s.equals("south") || s.equals("s")) {
            location = rooms.get(location).getsRoom().getRoomName();
        }
        else if (s.equals("west") || s.equals("w")) {
            location = rooms.get(location).getwRoom().getRoomName();
        }
        else
            System.out.println("Invalid input. Please use /north/east/south/west/n/e/s/w");
        if (location.equals("Nothing")){
            System.out.println("You cannot go in this direction.");
            location = temp;
        }
        return location;
    }

    void examine(HashMap<String, Item> inventory, String name){
        if(inventory.containsKey(name)){
            System.out.println(inventory.get(name).description);
        }
        else
            System.out.println("You are not carrying this Key.");
    }
    void pick(String name, HashMap<String, Item> inventory, Room aRoom){
        if(aRoom.items.containsKey(name)) {
            if (aRoom.getItems().get(name).pickable) {
                inventory.put(name, aRoom.getItems().get(name));
                aRoom.getItems().remove(name);
            }
            else
                System.out.println("This item is not pickable");
        }
        else{
            System.out.println("There is no such item at this location");
        }

    }
    void use(HashMap<String, Item> inventory, String name, Room aRoom, String location){
        if(inventory.containsKey(name) && (name.equals("Money")||name.equals("LostMoney")) ){
            if (location.equals("Restaurant")){
                inventory.put("Food", aRoom.getItems().get("Food"));
                aRoom.getItems().remove("Food");
                inventory.remove(name);
            }
            if (location.equals("Stable")) {
                inventory.put("Horse", aRoom.getItems().get("Horse"));
                aRoom.getItems().remove("Horse");
                inventory.remove(name);
            }
        }
        else
            System.out.println("You don't have any items that you can use");
    }

    void drop(String name, HashMap<String, Item> inventory, Room aRoom){
        if(inventory.containsKey(name)) {
            aRoom.getItems().put(name, inventory.get(name));
            inventory.remove(name);
        }
        else{
            System.out.println("There is no such item in your inventory");
        }
    }
}

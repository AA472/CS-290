import javax.management.Descriptor;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    HashMap<String, Item> items = new HashMap<>();
    private boolean visited = false;
    private String roomName;
    private String longDes;
    private String shortDes;
    private String directions;
    private Room wRoom;
    private Room eRoom;
    private Room nRoom;
    private Room sRoom;

    Room(String roomName){
        this.roomName = roomName;
    }
    Room(String roomName, String longDes, String shortDes, Room north,
         Room east, Room south, Room west, String directions) {
        this.roomName = roomName;
        this.longDes = longDes;
        this.shortDes = shortDes;
        this.nRoom = north;
        this.eRoom = east;
        this.sRoom = south;
        this.wRoom = west;
        this.directions = directions;
    }

    public void look(){
        System.out.print("Items here are: ");
        if (items.keySet().isEmpty())
            System.out.println("None");
        else{
            for(String i : items.keySet()) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = true;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getLongDes() {
        return longDes;
    }

    public String getShortDes() {
        return shortDes;
    }

    public String getDirections() {
        return directions;
    }

    public Room getwRoom() {
        return wRoom;
    }

    public Room geteRoom() {
        return eRoom;
    }

    public Room getnRoom() {
        return nRoom;
    }

    public Room getsRoom() {
        return sRoom;
    }

    public void setRestOfValues(String longDes, String shortDes,Room nRoom, Room eRoom, Room sRoom, Room wRoom, String directions){
        this.longDes = longDes;
        this.shortDes = shortDes;
        this.nRoom = nRoom;
        this.eRoom = eRoom;
        this.sRoom = sRoom;
        this.wRoom = wRoom;
        this.directions = directions;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }
    public void describeRoom(){
        if(visited){
            System.out.println(shortDes);
        }
        else
            System.out.println(longDes);
        System.out.println(directions);
    }
}

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Readfile {
    Scanner x;
    Scanner y;

    public void read(HashMap<String,Room> rooms){

        openFile();
        while(x.hasNext()) {
            x.next();
            String roomName = x.next();
            x.nextLine();
            x.nextLine();
            String longDes = x.nextLine();
            x.nextLine();
            String shortDes = x.nextLine();
            x.next();
            String north = x.next();
            x.next();
            String east = x.next();
            x.next();
            String south = x.next();
            x.next();
            String west = x.next();
            x.nextLine();
            String directions = x.nextLine();
            x.nextLine();
            Room nRoom = null;
            Room eRoom = null;
            Room sRoom = null;
            Room wRoom = null;

            if(north!="Nothing" && !(rooms.containsKey(north))) {
                nRoom = new Room(north);
                rooms.putIfAbsent(north,nRoom);
            }
            else if (rooms.containsKey(north))
                nRoom = rooms.get(north);
            if(east!="Nothing" && !(rooms.containsKey(east))) {
                eRoom = new Room(east);
                rooms.put(east, eRoom);
            }
            else if (rooms.containsKey(east))
                eRoom = rooms.get(east);
            if(south!="Nothing" && !(rooms.containsKey(south))) {
                sRoom = new Room(south);
                rooms.put(south, sRoom);
            }
            else if (rooms.containsKey(south))
                sRoom = rooms.get(south);
            if(west!="Nothing" && !(rooms.containsKey(west))) {
                wRoom = new Room(west);
                rooms.put(west, wRoom);
            }
            else if (rooms.containsKey(west))
                wRoom = rooms.get(west);
            if(!rooms.containsKey(roomName)) {
                Room aRoom = new Room(roomName, longDes, shortDes, nRoom, eRoom, sRoom, wRoom, directions);
                rooms.put(roomName, aRoom);
            }
            else
                rooms.get(roomName).setRestOfValues(longDes,shortDes,nRoom,eRoom,sRoom,wRoom,directions);
        }
        while(y.hasNext()){
            y.next();
            String itemName = y.next();
            y.nextLine();
            y.next();
            String location = y.next();
            y.nextLine();
            y.next();
            String description  = y.nextLine();
            y.next();
            String pickAble = y.next();
            y.nextLine();
            y.nextLine();

            Item aItem = new Item(itemName,description,pickAble);
            rooms.get(location).getItems().put(itemName,aItem);

        }
        closeFile();
    }
    public void openFile(){
        try {
            x = new Scanner(new File("Story.txt"));
        }catch(Exception e) {
            System.out.println("Could not find file.");
        };
        try {
            y = new Scanner(new File("Items.txt"));
        }catch(Exception e) {
            System.out.println("Could not find file.");
        };
    }
    public void closeFile(){
        x.close();
    }
}

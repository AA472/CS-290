import java.util.ArrayList;
import java.util.HashMap;

public class Item {
    String name;
    String description;
    boolean pickable = false;


    Item(String itemName, String description, String pickable){
        name = itemName;
        this.description  = description;
        if(pickable.equals("Yes"))
            this.pickable = true;
    }
}

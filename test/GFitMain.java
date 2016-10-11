import java.util.List;

import edu.baylor.googlefit.FoodManager;
import edu.baylor.googlefit.GFitFoodManager;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

/**
 * Creates a FoodManager using Google Fit.  Make sure to use your own
 * project number and JSON authentication file.  See README for details.
 *
 * Note:  This uses your FoodNetwork classes
 */
public class GFitMain {
    public static void main(String[] args) throws FoodNetworkException {
        FoodManager mgr = new GFitFoodManager("foodnetwork-146101", "./cred.json");
        mgr.addFood("Plum", MealType.Breakfast, 3, "5.6");
        List<FoodItem> itemList = mgr.getFoodItems();
        for (FoodItem i : itemList) {
            System.out.println(i);
        }
    }
}


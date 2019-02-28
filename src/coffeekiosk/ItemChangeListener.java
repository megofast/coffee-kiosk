/*
*    ItemChangeListener.java
*    By: David
*/
package coffeekiosk;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.util.ArrayList;

public class ItemChangeListener implements ItemListener {
    private JTextArea textArea;
    private ArrayList<String> summary = new ArrayList<String>();
    private int itemType;   // 1 = toggle button, 2 = combo box
    private int cupIndex;
    private String lastSelected = CoffeeKiosk.flavors[0];   // Sets the default to black coffee
    
    private void itemSelected(String text) {
        summary.add(text);
        textArea.append(text + "\n");
    }
    
    private void itemDeselected(String text) {
        textArea.setText("");
        
        // remove the entry in the list that was deselected
        if (this.itemType == 1) {
            summary.remove(summary.indexOf(text));
        }
        else if (this.itemType == 2) {
            summary.remove(summary.indexOf(this.lastSelected));
        }
            
        textArea.setText("Coffee Summary \n ------------------------- \n");
        int x;
        for (x = 0; x < summary.size(); x++) {
            textArea.append(summary.get(x) + "\n");
        }
    }
    
    ItemChangeListener(JTextArea jt, ArrayList sum, int type, int cIndex) {
        this.textArea = jt;
        this.summary = sum;
        this.itemType = type;
        this.cupIndex = cIndex - 1;
    }
    
    public static void calculateCost() {
        CoffeeKiosk.totalPrice = 0;
        int x;
        for (x = 0; x < CoffeeKiosk.coffee.size(); x++){
            CoffeeKiosk.totalPrice = CoffeeKiosk.totalPrice + CoffeeKiosk.coffee.get(x).price;
        }
        CoffeeKiosk.price.setText("$" + CoffeeKiosk.totalPrice);
    }
    
    /*
    * This method will update the variables in the arraylist of coffee cups, so 
    * the variables can be used for calculations.
    */
    private void updateCoffee(String text, int addDel) {
        if (addDel == 0) {
            if (text.equalsIgnoreCase("creamer")) {
                CoffeeKiosk.coffee.get(cupIndex).creamer = true;
            }
            if (text.equalsIgnoreCase("sugar")) {
                CoffeeKiosk.coffee.get(cupIndex).sugar = true;
            }
            if (text.equalsIgnoreCase("sweetener")) {
                CoffeeKiosk.coffee.get(cupIndex).sweetener = true;
            }

            // Set the size of the coffee in the coffee cup class
            if (text.equalsIgnoreCase("small")) {
                CoffeeKiosk.coffee.get(cupIndex).size = 0;
            }
            else if (text.equalsIgnoreCase("medium")) {
                CoffeeKiosk.coffee.get(cupIndex).size = 1;
            }
            else if (text.equalsIgnoreCase("large")) {
                CoffeeKiosk.coffee.get(cupIndex).size = 2;
            }
            else if (text.equalsIgnoreCase("extra large")) {
                CoffeeKiosk.coffee.get(cupIndex).size = 3;
            }
            
            // calculate cost and add price to gui
            CoffeeKiosk.coffee.get(cupIndex).calculateCost();
        }
        else if (addDel == 1) {
            if (text.equalsIgnoreCase("creamer")) {
                CoffeeKiosk.coffee.get(cupIndex).creamer = false;
            }
            if (text.equalsIgnoreCase("sugar")) {
                CoffeeKiosk.coffee.get(cupIndex).sugar = false;
            }
            if (text.equalsIgnoreCase("sweetener")) {
                CoffeeKiosk.coffee.get(cupIndex).sweetener = false;
            }
        }
        calculateCost();
        
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        String srcText;
        if (this.itemType == 1) {
            JToggleButton src = (JToggleButton)e.getSource();
            srcText = src.getText();
        }
        else {
            JComboBox src = (JComboBox)e.getSource();
            srcText = (String)src.getSelectedItem();
        }
        
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // item selected
            itemSelected(srcText);
            updateCoffee(srcText, 0);
            if (this.itemType == 2) {
                this.lastSelected = srcText;
            }
        }
        else {
                updateCoffee(srcText, 1);
                itemDeselected(srcText);
        }
    }
}

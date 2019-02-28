
package coffeekiosk;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.*;

public class CoffeeCup {
    public int ID;
    public String flavor;
    public boolean creamer;
    public boolean sugar;
    public boolean sweetener;
    public double cost;
    public double price;
    public String strPrice;
    public int size;
    public int amountWater;   // Stores the amount of water needed to make this cup
    public ArrayList<String> summary = new ArrayList<>();
    
    public void formatCost() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        this.strPrice = formatter.format(this.price);
    }
    
    public void calculateCost() {
        switch(this.size){
            case 0: this.price = 1.25; this.amountWater = 8; break;
            case 1: this.price = 1.50; this.amountWater = 12; break;
            case 2: this.price = 1.75; this.amountWater = 16; break;
            case 3: this.price = 2.00; this.amountWater = 20; break;
            default: break;
        }
        formatCost();
    }
    
    public void createTabGUI(JTabbedPane tabPane, int index) {
        JPanel tab = (JPanel)tabPane.getComponentAt(index);
        int x = index + 1;   // X represents the coffee cup index, ie cup 1, 2, 3, 4, or 5
        
        
        // Set the layout for the panel
        tab.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        ButtonGroup group = new ButtonGroup();
        
        // Insert an option for coffee cup size
        JToggleButton smallBtn = new JToggleButton("Small");
        c.fill = GridBagConstraints.HORIZONTAL;    // This fill property will stay the same for all objects
        c.insets = new Insets(5,5,5,5);    // This inset (exterior offset will stay the same for all objects!
        c.gridx = 0;
        c.gridy = 0;
        tab.add(smallBtn, c);
        group.add(smallBtn);
        
        JToggleButton mediumBtn = new JToggleButton("Medium");
        c.gridx = 1;
        c.gridy = 0;
        tab.add(mediumBtn, c);
        group.add(mediumBtn);
        
        JToggleButton largeBtn = new JToggleButton("Large");
        c.gridx = 2;
        c.gridy = 0;
        tab.add(largeBtn, c);
        group.add(largeBtn);
        
        JToggleButton xLargeBtn = new JToggleButton("Extra Large");
        c.gridx = 3;
        c.gridy = 0;
        tab.add(xLargeBtn, c);
        group.add(xLargeBtn);
        
        JLabel flavLabel = new JLabel("Select your flavor: ");
        c.gridx = 0;
        c.gridy = 1;
        tab.add(flavLabel, c);
        
        JComboBox coffeeFlavor = new JComboBox(CoffeeKiosk.flavors);
        c.gridx = 1;
        c.gridy = 1;
        tab.add(coffeeFlavor, c);
        
        // Add the default coffee flavor of black
        this.flavor = CoffeeKiosk.flavors[0];
        this.summary.add(CoffeeKiosk.flavors[0]);
        
        JToggleButton creamerBtn = new JToggleButton("Creamer");
        c.gridx = 0;
        c.gridy = 2;
        tab.add(creamerBtn, c);
        
        JToggleButton sugarBtn = new JToggleButton("Sugar");
        c.gridx = 1;
        c.gridy = 2;
        tab.add(sugarBtn, c);
        
        JToggleButton sweetenerBtn = new JToggleButton("Sweetener");
        c.gridx = 0;
        c.gridy = 3;
        tab.add(sweetenerBtn, c);
        
        // Add a text area that will summarize this coffees order
        JTextArea txtSummary = new JTextArea(10, 10);
        txtSummary.setEditable(false);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        txtSummary.setText("Coffee Summary \n ------------------------- \n" + summary.get(0) + "\n");
        tab.add(txtSummary, c);
        
        // Add the item listeners for buttons
        smallBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        mediumBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        largeBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        xLargeBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        creamerBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        sugarBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        sweetenerBtn.addItemListener(new ItemChangeListener(txtSummary, summary, 1, x));
        coffeeFlavor.addItemListener(new ItemChangeListener(txtSummary, summary, 2, x));
        
        smallBtn.doClick();   // This simulates a users click, to set the default coffee size as small
    }
    
    
}

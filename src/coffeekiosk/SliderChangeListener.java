
package coffeekiosk;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.*;
import java.util.ArrayList;


public class SliderChangeListener implements ChangeListener {
    private JTabbedPane coffeePane;
    public ArrayList<CoffeeCup> coffees = new ArrayList<CoffeeCup>();
    private int oldSliderValue = 1;   // Used to store the previous slider position
    private final String[] TABLABELS = {"Coffee 1", "Coffee 2", "Coffee 3", "Coffee 4", "Coffee 5", "Coffee 6", "Coffee 7", "Coffee 8", "Coffee 9", "Coffee 10"};
    
    SliderChangeListener(JTabbedPane p, ArrayList c) {
        this.coffeePane = p;
        this.coffees = c;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        
        if (!source.getValueIsAdjusting()) {
            // The slider was slid, determine if it was to add or remove cups
            if (source.getValue() > oldSliderValue) {
                addTab(source.getValue() - oldSliderValue);
            }
            else if (source.getValue() < oldSliderValue) {
                removeTab(oldSliderValue - source.getValue());
            }
            oldSliderValue = source.getValue();
        }
    }
    
    public void addTab(int numberToAdd) {
        // This method will add a tab to the coffee tabs menu
        if (numberToAdd == 1) {
            JPanel tab = new JPanel();
            coffeePane.addTab(TABLABELS[coffeePane.getTabCount()], tab);
            
            // The next two lines add a coffeeCup instance to an arraylist
            coffees.add(new CoffeeCup());
            coffees.get(coffees.size() - 1).createTabGUI(coffeePane, coffees.size() - 1);
        }
        else if (numberToAdd > 1) {
            // The user moved the slider more than one, add more than 1 tab
            int x;
            for (x = 0; x < numberToAdd; x++) {
                JPanel tab = new JPanel();
                coffeePane.addTab(TABLABELS[coffeePane.getTabCount()], tab);
                
                coffees.add(new CoffeeCup());
                coffees.get(coffees.size() - 1).createTabGUI(coffeePane, coffees.size() - 1);
            }
        }
    }
    
    public void removeTab(int numberToRemove) {
        // This method will remove the last tab each time
        if (numberToRemove == 1) {
            coffeePane.removeTabAt(coffeePane.getTabCount() - 1);
            
            // Remove the coffeecup class from the array if the tab is removed
            coffees.remove(coffees.size() - 1);
        }
        else if (numberToRemove > 1) {
            int x;
            // Loop through to remove the last label however many ticks the user slid the slider
            for (x = 0; x < numberToRemove; x++) {
                coffeePane.removeTabAt(coffeePane.getTabCount() - 1);
                
                // remove the coffeecup class from the array if the tab is removed
                coffees.remove(coffees.size() - 1);
            }
        }
        
        // Update the cost when a tab is removed
        ItemChangeListener.calculateCost();
    }
}
        
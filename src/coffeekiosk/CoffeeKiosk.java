
package coffeekiosk;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CoffeeKiosk {

    public static JLabel price = new JLabel("$1.25");
    public static double totalPrice = 0;
    public static String[] flavors = {"Black", "Vanilla", "French", "Hazlenut"};
    public static ArrayList<CoffeeCup> coffee = new ArrayList<CoffeeCup>();
    
    private static ArrayList createCoffeeCups(JTabbedPane pane) {
        
        coffee.add(new CoffeeCup());
        
        // Create the first array list tab since there will always be one tab
        coffee.get(0).createTabGUI(pane, 0);
        
        return coffee;
    }
    
    private static void setupBottomBar(JFrame f) {
        // Add a new pane on the bottom to change the layout to a horizontal layout
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
        String fTime = time.format(timeFormat);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        String fDate = date.format(dateFormat);
        String dateTime = fDate + " - " + fTime;
        
        // Create the panel for all the bottom objects to be arranged into
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        
        f.getContentPane().add(bottomBar);
        
        JLabel currentDate = new JLabel(dateTime);
        currentDate.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        currentDate.setFont(new Font("Serif", Font.PLAIN, 12));
        bottomBar.add(currentDate);
        
        price.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        bottomBar.add(price);
        
        JButton btnService = new JButton("Service");
        btnService.setFont(new Font("Serif", Font.PLAIN, 10));
        btnService.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bottomBar.add(btnService);
        btnService.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceGUI.initializeGUI(coffee, f);
                f.setVisible(false);
            }
        });
        
        JLabel space = new JLabel("     ");
        bottomBar.add(space);
        
        JButton btnNext = new JButton("Checkout");
        bottomBar.add(btnNext);
        
        // Add an action listener to the button, I chose to do so inline instead of a
        // seperate class, since all the action listener needs to do is open the next page.
        btnNext.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderSummary.initWindow(coffee, f);   // Calls the summary window
                //f.removeAll();   // Removes all components on original window
                f.setVisible(false);
            }
        });
    }
    
    public static void initializeGUI() {        
        JFrame frame = new JFrame("Coffee Kiosk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        // Add a panel for the top bar
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout(5,5));
        frame.getContentPane().add(topBar);
        
        JLabel label1 = new JLabel("How many cups of coffee?");
        label1.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        topBar.add(label1, BorderLayout.LINE_START);
        
        // Create a slider for the number of cups of coffee
        JSlider coffeeSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        
        coffeeSlider.setMajorTickSpacing(1);
        coffeeSlider.setPaintLabels(true);
        coffeeSlider.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        coffeeSlider.setFont(new Font("Serif", Font.PLAIN, 12));
        topBar.add(coffeeSlider, BorderLayout.CENTER);
        
        // Create a tab pane for each cup of coffee, up to 5
        JTabbedPane coffeeTabs = new JTabbedPane();
        coffeeTabs.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JPanel tab = new JPanel();
        coffeeTabs.addTab("Coffee 1", tab);
        frame.getContentPane().add(coffeeTabs);
        
        // Add a change listener for the slider and create the arraylist of coffeecups
        coffeeSlider.addChangeListener(new SliderChangeListener(coffeeTabs, createCoffeeCups(coffeeTabs)));
        
        // Setup the bottom bar, needed a new layout directions
        setupBottomBar(frame);
        
        // Finish setting up the main window
        //frame.setSize(450, 500);
        frame.pack();
        
        // This centers the window on the screen
        frame.setLocationRelativeTo(null);
        
        // make the screen visible
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Call the initialization method to start showing the user interface
        initializeGUI();
    }
    
}

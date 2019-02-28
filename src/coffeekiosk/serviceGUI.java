/*
*    serviceGUI.java
*    By: David
*/
package coffeekiosk;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.text.NumberFormat;


public class serviceGUI {
    private static JFrame pFrame;
    public static int waterReserve;
    public static double totalSales;
    
    public static void saveData() {
        try {
            PrintWriter writer = new PrintWriter("data.txt", "UTF-8");
            writer.println(waterReserve);
            writer.println(totalSales);
            writer.close();
        }
        catch (IOException e) {
            
        }
        
    }
    
    public static void getData() {
        String line;
        int x = 0;
        
        try {
            FileReader reader = new FileReader("data.txt");
            BufferedReader bReader = new BufferedReader(reader);
            
            while ((line = bReader.readLine()) != null) {
                // get the data and store it into variables
                if (x == 0) {
                    waterReserve = Integer.valueOf(line);
                }
                else if (x == 1) {
                    totalSales = Double.valueOf(line);
                }
                x++;
            }
        }
        catch (IOException e) {
            
        }
    }
    
    private static void setupComponents(JFrame f, GridBagConstraints c) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        
        getData();   // Get the data to display in the service window
        
        JLabel lblTitle = new JLabel("Service Menu");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 0;
        f.add(lblTitle, c);
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        f.add(sep, c);
        
        JLabel lblWater = new JLabel("Water Remaining:");
        c.gridwidth = 1;
        c.gridy = 2;
        f.add(lblWater, c);
        
        JLabel water = new JLabel("100 oz");
        c.gridx = 1;
        c.gridy = 2;
        f.add(water, c);
        water.setText(String.valueOf(waterReserve) + " oz");
        
        JTextField addWater = new JTextField(5);
        c.gridx = 2;
        c.gridy = 2;
        f.add(addWater, c);
        
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        f.add(sep1, c);
        
        JLabel lblSales = new JLabel("Total Sales:");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        f.add(lblSales, c);
        
        JLabel sales = new JLabel("$25.00");
        c.gridx = 1;
        c.gridy = 4;
        f.add(sales, c);
        String totals = formatter.format(totalSales);
        sales.setText(totals);
        
        JButton btnBack = new JButton("Back");
        c.gridx = 0;
        c.gridy = 5;
        f.add(btnBack, c);
        backButtonClick(btnBack, f);
        
        JButton btnUpdate = new JButton("Update");
        c.gridx = 2;
        c.gridy = 5;
        f.add(btnUpdate, c);
        updateButtonClick(btnUpdate, f, addWater, water);
    }
    
    private static void updateButtonClick(JButton btn, JFrame f, JTextField txt, JLabel lbl) {
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                waterReserve = waterReserve + Integer.valueOf(txt.getText());
                lbl.setText(String.valueOf(waterReserve));
                saveData();
            }
        });
    }
    
    private static void backButtonClick(JButton btn, JFrame f) {
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                pFrame.setVisible(true);
                f.setVisible(false);
            }
        });
    }
    
    public static void initializeGUI(ArrayList<CoffeeCup> coffees, JFrame prevFrame) {
        JFrame frame = new JFrame("Coffee Kiosk - Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        pFrame = prevFrame;
        setupComponents(frame, c);
        
        frame.pack();
        
        // This centers the window on the screen
        frame.setLocationRelativeTo(null);
        
        // make the screen visible
        frame.setVisible(true);
    }
}

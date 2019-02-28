/*
*    Receipt.java
*    By: David
*/
package coffeekiosk;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Font;


public class Receipt {

    private static void setupComponents(JFrame f, GridBagConstraints c, ArrayList<CoffeeCup> coffee) {
        JLabel lblThanks = new JLabel("Thank You! Enjoy!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;
        f.add(lblThanks, c);
        
        JTextArea receipt = new JTextArea(20, 15);
        receipt.setEditable(false);
        c.gridx = 0;
        c.gridy = 1;
        f.add(receipt, c);
        
        receipt.setFont(new Font("monospaced", Font.PLAIN, 12));
        
        // Fill the text area with all the summary data
        int x;
        for (x = 0; x < coffee.size(); x++) {
            // Loop through for the total number of coffees
            receipt.append("Coffee " + x + "                    " + coffee.get(x).strPrice + "\n");
            int y;
            for (y = 0; y < coffee.get(x).summary.size(); y++) {
                // loop through all the entrys in the coffee summary field
                receipt.append("   " + coffee.get(x).summary.get(y) + "\n");
            }
        }
        
        receipt.append("--------------------------------\n");
        receipt.append("Sub Total:                  " + OrderSummary.strSubTotal + "\n");
        receipt.append("State Tax (4%):             " + OrderSummary.strStateTax + "\n");
        receipt.append("County Tax (2%):            " + OrderSummary.strCountyTax + "\n");
        receipt.append("Coupon:                   - " + OrderSummary.strDiscount + "\n");
        receipt.append("Total:                      " + OrderSummary.strGrossTotal + "\n");
    }
    
    public static void initializeReceipt(ArrayList<CoffeeCup> coffees) {      
        JFrame frame = new JFrame("Coffee Kiosk - RECEIPT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Create all the compontents on the summary screen
        setupComponents(frame, c, coffees);
        
        frame.pack();
        
        // This centers the window on the screen
        frame.setLocationRelativeTo(null);
        
        // make the screen visible
        frame.setVisible(true);
    }
}

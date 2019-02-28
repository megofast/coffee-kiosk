/*
*    OrderSummary.java
*    By: David
*/
package coffeekiosk;

import static coffeekiosk.CoffeeKiosk.coffee;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.text.NumberFormat;


public class OrderSummary {
    public static double stateTax;
    public static double countyTax;
    public static double gTotal;
    public static double discount;
    public static int totalWater;
    public static String strStateTax;
    public static String strCountyTax;
    public static String strGrossTotal;
    public static String strDiscount;
    public static String strSubTotal;
    private static JFrame pFrame;
    
    public static void formatMoney() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        strStateTax = formatter.format(stateTax);
        strCountyTax = formatter.format(countyTax);
        strGrossTotal = formatter.format(gTotal);
        strDiscount = formatter.format(discount);
        strSubTotal = formatter.format(CoffeeKiosk.totalPrice);
    }
    
    public static void calculateTotal(ArrayList<CoffeeCup> coffee) {
        int x;
        CoffeeKiosk.totalPrice = 0;
        for (x = 0; x < coffee.size(); x++) {
            CoffeeKiosk.totalPrice = CoffeeKiosk.totalPrice + coffee.get(x).price;
            totalWater = totalWater + coffee.get(x).amountWater;
        }
        
        stateTax = CoffeeKiosk.totalPrice * 0.04;
        countyTax = CoffeeKiosk.totalPrice * 0.02;
        
        gTotal = CoffeeKiosk.totalPrice + stateTax + countyTax;
    }
    
    public static void applyCoupon(JLabel prevTotal, JLabel disc) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        discount = gTotal * 0.1;
        gTotal = gTotal - discount;
        strGrossTotal = formatter.format(gTotal);
        strDiscount = formatter.format(discount);
        prevTotal.setText(strGrossTotal);
        disc.setText(strDiscount);
    }
    
    public static void setupComponents(JFrame f, GridBagConstraints c, ArrayList<CoffeeCup> coffee) {
        JTextArea[] summary = new JTextArea[5];
        int x;
        for (x = 0; x < 5; x++) {
            summary[x] = new JTextArea(10, 10);
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,5,5);
        c.gridy = 0;
        if (coffee.size() >= 1) {
            c.gridx = 0;
            f.add(summary[0], c);
        }
        if (coffee.size() >= 2) {
            c.gridx = 1;
            f.add(summary[1], c);
        }
        if (coffee.size() >= 3) {
            c.gridx = 2;
            f.add(summary[2], c);
        }
        if (coffee.size() >= 4) {
            //c.gridy = 1;
            c.gridx = 3;
            f.add(summary[3], c);
        }
        if (coffee.size() >= 5) {
            c.gridx = 4;
            f.add(summary[4], c);
        }
        
        // Set the summary descriptions into each textarea
        for (x = 0; x < coffee.size(); x++) {
            // Loop through all the summary textareas
            summary[x].setText("Coffee " + x + " Summary \n ------------------------- \n");
            int y;
            for (y = 0; y < coffee.get(x).summary.size(); y++) {
                // loop through all the entrys in the coffee summary field
                summary[x].append(coffee.get(x).summary.get(y) + "\n");
            }
            summary[x].append("--------------------\n");
            summary[x].append(coffee.get(x).strPrice);
        }
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        f.add(sep, c);
        
        JLabel lblSTotal = new JLabel("Sub Total: ");
        c.gridx = 0;
        c.gridy = 3;
        f.add(lblSTotal, c);
        
        JLabel sTotal = new JLabel("$1.25");
        sTotal.setText(strSubTotal);
        c.gridx = 1;
        c.gridy = 3;
        f.add(sTotal, c);
        
        JLabel lblTax = new JLabel("State Tax (4%): ");
        c.gridx = 0;
        c.gridy = 4;
        f.add(lblTax, c);
        
        JLabel tax = new JLabel("$0.50");
        tax.setText(strStateTax);
        c.gridx = 1;
        c.gridy = 4;
        f.add(tax, c);
        
        JLabel lblCTax = new JLabel("County Tax (2%): ");
        c.gridx = 0;
        c.gridy = 5;
        f.add(lblCTax, c);
        
        JLabel cTax = new JLabel("$2.00");
        cTax.setText(strCountyTax);
        c.gridx = 1;
        c.gridy = 5;
        f.add(cTax, c);
        
        JLabel lblDiscount = new JLabel("Discount:   -");
        c.gridx = 0;
        c.gridy = 6;
        f.add(lblDiscount, c);
        
        JLabel discount = new JLabel("$0.00");
        c.gridx = 1;
        c.gridy = 6;
        f.add(discount, c);
        
        JLabel lblTotal = new JLabel("Total: ");
        c.gridx = 0;
        c.gridy = 7;
        f.add(lblTotal, c);
        
        JLabel total = new JLabel("$2.00");
        total.setText(strGrossTotal);
        c.gridx = 1;
        c.gridy = 7;
        f.add(total, c);
        
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 5;
        f.add(sep1, c);
        
        JLabel lblCoup = new JLabel("Coupon Code:");
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        f.add(lblCoup, c);
        
        JTextField coup = new JTextField(10);
        c.gridx = 1;
        c.gridy = 9;
        f.add(coup, c);
        
        JButton btnApply = new JButton("Apply");
        c.gridx = 2;
        c.gridy = 9;
        f.add(btnApply, c);
        
        btnApply.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check the user input in the coupon field, apply discount if oode matches
                String userInput = coup.getText();
                if (userInput.equalsIgnoreCase("coffee")) {
                    // Apply discount
                    applyCoupon(total, discount);
                    coup.setText("10% Discount");
                    coup.setEditable(false);
                    btnApply.setEnabled(false);
                }
            }
        });
        
        JLabel lblMethod = new JLabel("Payment Method:");
        c.gridx = 0;
        c.gridy = 10;
        f.add(lblMethod, c);
        
        String[] methods = {"Mastercard", "Visa", "Discover"};
        JComboBox paymentMethod = new JComboBox(methods);
        c.gridx = 1;
        c.gridy = 10;
        c.gridwidth = 2;
        f.add(paymentMethod, c);
        
        JLabel lblCard = new JLabel("Card Number:");
        c.gridx = 0;
        c.gridy = 11;
        f.add(lblCard, c);
        
        JTextField card = new JTextField(10);
        c.gridx = 1;
        c.gridy = 11;
        c.gridwidth = 2;
        f.add(card, c);
        
        JButton backBtn = new JButton("Back");
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 1;
        f.add(backBtn, c);
        backButtonClick(backBtn, f);   // This adds a click event to the button
        
        JButton payBtn = new JButton("Pay");
        c.gridx = 2;
        c.gridy = 12;
        f.add(payBtn, c);
        payButtonClick(payBtn, f, card);   // Adds the click even for the pay button
        
    }
    
    private static boolean validateCard(JTextField card) {
        String strCard = card.getText();
        int x;
        if (strCard.length() == 16) {
            for (x = 0; x < strCard.length(); x++) {
                if (Character.isDigit(strCard.charAt(x))) {
                    // The character is a digit, continue looping through
                }
                else {
                    // The card doesnt have a digit in this position, break the loop and return false
                    return false;
                }
            }
        }
        else {
            // Incorrect length
            return false;
        }
        return true;
    }
    
    private static boolean validateWater() {
        serviceGUI.getData();
        if (totalWater > serviceGUI.waterReserve) {
            // There is not enough water to complete this order
            return false;
        }
        else {
            return true;
        }
    }
    
    private static void payButtonClick(JButton btn, JFrame f, JTextField card) {
        validateCard(card);
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateCard(card)) {
                    if (validateWater()) {
                        serviceGUI.getData();
                        serviceGUI.totalSales = serviceGUI.totalSales + gTotal;
                        serviceGUI.waterReserve = serviceGUI.waterReserve - totalWater;
                        serviceGUI.saveData();
                        Receipt.initializeReceipt(coffee);
                        f.setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(f, "Sorry, There is not enough water to complete the order...");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(f, "The card entered is invalid, please enter 16 digits!");
                }
            }
        });
    }
    
    private static void backButtonClick(JButton btn, JFrame f) {
        btn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalWater = 0;
                pFrame.setVisible(true);
                f.setVisible(false);
            }
        });
    }
    
    public static void initWindow(ArrayList<CoffeeCup> coffees, JFrame prevFrame) {
        // Set the pFrame variable to the old frame in case the user wants to go back
        pFrame = prevFrame;
        
        JFrame frame = new JFrame("Coffee Kiosk - Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        calculateTotal(coffees);
        
        formatMoney();   // Format the output of the prices
        
        // Create all the compontents on the summary screen
        setupComponents(frame, c, coffees);
        
        frame.pack();
        
        // This centers the window on the screen
        frame.setLocationRelativeTo(null);
        
        // make the screen visible
        frame.setVisible(true);
    }
}

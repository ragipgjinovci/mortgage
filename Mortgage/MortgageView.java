// Importing needed libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
// Creating the view
public class MortgageView extends JPanel {
    private JFrame frame;
    private DecimalFormat format;
    private double p;
    private double i;
    private Integer y;
    private double monthly_payment;
    private double total_paid;
    private double remaining_principal;
    private JButton b;
    private JButton reset;
    public boolean nullValues = true;
    public boolean lastPay = false;
    public boolean clicked = false;
    // Constructor
    public MortgageView() {
        format = new DecimalFormat("0.000");
        frame= new JFrame();
        frame.setLocationByPlatform(true);
        frame.setTitle("Mortagage Calculator");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(480,270);
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        if(nullValues) {
            b = new JButton("MAKE MONTHLY PAYMENT");
            reset = new JButton("Reset Values");
        }
    }
    // Methods that handle events
    // Method that sets a window listener on frame for closing event
    public void setWL(WindowListener wl) {
        frame.addWindowListener(wl);
    }
    // Method that sets an action listener on the MAKE MONTHLY PAYMENT button
    public void setBL(ActionListener bl) {
        b.addActionListener(bl);
    }
    // Method that sets an action listener on the RESET button
    public void setRL(ActionListener rl) {
        reset.addActionListener(rl);
    }
    // Methods that get answer from users for closing and reseting values
    // Getting answer for closing
    public int closingMethod() {
        int answer = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to close this window?", "Close Window?", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        return answer;
    }
    // Getting answer for reset
    public int resetMethod() {
        int answer = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to reset the values?", "Reset values?", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        return answer;
    }
    // Methods for getting user's input for the required values
    // Get Starting Principal Method
    public double getPrincipal() {
        double p = 0;
        try {
            p = Double.parseDouble(JOptionPane.showInputDialog("Starting principal: "));
            if(p <= 0) {
               JOptionPane.showMessageDialog(null, "Starting principal must be greater than 0");
               p = getPrincipal();
               return p;
            }
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Starting principal must be a number", "Error - Starting Principal", JOptionPane.ERROR_MESSAGE);
            p = getPrincipal();
            return p;
        }
        return p;
    }
    // Get Interest Method
    public double getInterest() {
        double i = 0;
        try {
            i = Double.parseDouble(JOptionPane.showInputDialog("Interest rate: "));
            if(i <= 0 || i >= 1) {
                JOptionPane.showMessageDialog(null, "Interest rate must be between 0 and 1. For example 40% would be 0.4");
                i = getInterest();
                return i;
            }
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Interest rate must be a number");
            i = getInterest();
            return i;
        }
        return i;
    }
    // Get Years Value
    public Integer getYears() {
        try {
            String res = JOptionPane.showInputDialog("Years: ");
            if(res == null) {
               throw new NullPointerException();
            }
            else if(res == "") {
               throw new NumberFormatException();
            }
            else {
               int y = Integer.parseInt(res);
               if(y <= 0) {
                   JOptionPane.showMessageDialog(null, "Years must be greater than 0");
                   y = getYears();
                   return y;
               }
               return y;
            }
         }
        catch(NumberFormatException e) {
           JOptionPane.showMessageDialog(null, "Years must be a number");
           y = getYears();
           return y;
         }
         catch(NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Value cannot be null", "Error - Null Value", JOptionPane.ERROR_MESSAGE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
         }
        return y;
    }
    // Method that displays an error if the values are null
    public void nullError() {
        JOptionPane.showMessageDialog(null, "Value cannot be null", "Error - Null Value", JOptionPane.ERROR_MESSAGE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    // Method for setting current values to display
    // Method that sets the principal value
    public void setPrincipal(double p) {
        this.p = p;
    }
    // Method that sets the interest value
    public void setInterest(double i) {
        this.i = i;
    }
    // Method that sets the years value
    public void setYears(Integer y) {
        this.y = y;
    }
    // Method that sets the current monthly payment
    public void setMonthlyPayment(double mp) {
        monthly_payment = mp;
    }
    // Method that sets the current total paid value
    public void setTotalPaid(double tp) {
        total_paid = tp;
    }
    public void setRemainingPrincipal(double rp) {
        remaining_principal = rp;
    }
    // Paint component Method
    public void paintComponent(Graphics g) {
        // Header style
        g.setColor(Color.decode("#323232"));
        g.fillRect(0,0,480,270);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 480, 30);
        g.setColor(Color.decode("#989494"));
        g.setFont(new Font("Franklin Gothic", Font.BOLD, 18));
        g.drawString("Mortgage Calculator", 150, 20);
        // Main style
        g.setColor(Color.decode("#DA9A9A"));
        g.fillRect(0, 30, 480, 5);
        g.setColor(Color.decode("#989494"));
        g.setFont(new Font("Franklin Gothic", Font.BOLD, 14));
        g.drawString("For the following values: ", 20, 60);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Franklin Gothic", Font.PLAIN, 12));
        g.drawString("Starting principal: " + p, 20, 90);
        g.drawString("Interest rate: " + i, 20, 120);
        g.drawString("Years: " + y, 20, 150);
        // If we have an object
        if(!nullValues) {
            g.setColor(Color.decode("#989494"));
            g.setFont(new Font("Franklin Gothic", Font.BOLD, 14));
            g.drawString("The results are as follow: ", 220, 60);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Franklin Gothic", Font.PLAIN, 12));
            // Displaying monthly payment
            if(!clicked) {
                g.drawString("Monthly Payment: " + format.format(monthly_payment), 220, 90);
            }
            // Displaying results after making a monthly payment
            else {
                g.drawString("The remaining principal: " + format.format(remaining_principal), 220, 90);
                g.drawString("Total paid so far: " + format.format(total_paid), 220, 120);
            }
            b.setFont(new Font("Franklin Gothic", Font.BOLD, 10));
            b.setBounds(220,140,200,20);
            b.setForeground(Color.WHITE);
            b.setBackground(Color.decode("#DA9A9A"));
            b.setBorder(null);
            // If it is the last payment disable button
            if(lastPay) {
                b.setEnabled(false);
            }
            else {
                b.setEnabled(true);
            }
            reset.setFont(new Font("Franklin Gothic", Font.BOLD, 10));
            reset.setBounds(220, 165, 200, 20);
            reset.setForeground(Color.decode("#DA9A9A"));
            reset.setBackground(Color.WHITE);
            reset.setBorder(null);
            // Adding buttons to the view 
            this.add(b);
            this.add(reset);
         }
        // Footer style
        g.setColor(Color.BLACK);
        g.fillRect(0, 205, 480, 30);
        g.setColor(Color.decode("#989494"));
        g.setFont(new Font("Franklin Gothic", Font.PLAIN, 12));
        g.drawString("Developed by Ragip Gjinovci", 150, 222);
        g.setColor(Color.decode("#DA9A9A"));
        g.fillRect(0, 202, 480, 3);
    }
    //Repainting method
    public void repaintMethod() {
        this.repaint();
    }
}
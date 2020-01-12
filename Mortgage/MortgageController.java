// Importing needed libraries
import java.awt.event.*;
// Creating the controller
public class MortgageController {
    private static MortgageView view;
    private static Mortgage mort;
    private static boolean reset; 
    public static void main(String[] args) {
        view = new MortgageView();
        // Closing event
        view.setWL(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int answer = view.closingMethod(); // Getting the closing answer
                if (answer == 0) {
                    System.exit(0);
                }
                if(answer == 1 && (reset || view.nullValues)) {
                    createMortgage();
                    double mp = mort.getMonthlyPayment();
                    view.setMonthlyPayment(mp);
                }
            }
        });
        // Button MAKE MONTHLY PAYMENT Clicked event
        view.setBL(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
                view.clicked = true;
                mort.makeMonthlyPayment();
                view.setTotalPaid(mort.getTotalPaid());
                view.setRemainingPrincipal(mort.getRemainingPrincipal());
                if(mort.getRemainingPrincipal() < 0) {
                    view.lastPay = true;
                    view.setRemainingPrincipal(0);
                }
                view.repaintMethod();
            }     
        });
        // Button RESET Clicked event
        view.setRL(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int answer = view.resetMethod();
                reset = true;
                if (answer == 0) {
                    createMortgage();
                    double mp = mort.getMonthlyPayment();
                    view.setMonthlyPayment(mp);
                    view.clicked = false;
                    view.lastPay = false;
                }
            }  
        });
        createMortgage();
        if(!view.nullValues) {
            double mp = mort.getMonthlyPayment();
            view.setMonthlyPayment(mp);
        }
    }
    public static void createMortgage() {
        try {
            double p = view.getPrincipal();
            view.setPrincipal(p);
            view.repaint();
            double i = view.getInterest();
            view.setInterest(i);
            view.repaint();
            Integer y = view.getYears();
            view.setYears(y);
            view.repaint();
            view.nullValues = false;
            mort = new Mortgage(p, i, y);
        }
        catch(NullPointerException e) {
            view.nullError();
        }
    }    
}
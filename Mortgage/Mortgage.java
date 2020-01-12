// Creating the class
public class Mortgage {
    // Field variables
    private double monthly_payment;
    private double remaining_principal;
    private double total_paid;
    private double interest;
    private double payment;
    // Constructor in which the values are set
    public Mortgage(double p, double i, Integer y) {
        payment = (Math.pow(1+i, y) * p * i)/(Math.pow(1+i, y) - 1);
        monthly_payment = payment / 12.0;
        interest = i;
        remaining_principal = p;
    }
    // Returns Monthly Payment
    public double getMonthlyPayment() {
        return monthly_payment;
    }
    // Makes a monthly payment
    public void makeMonthlyPayment() {
        total_paid += monthly_payment;
        remaining_principal = ((1+(interest/12)) * remaining_principal) - payment;
    }
    // Returns remaining principal
    public double getRemainingPrincipal() {
        return remaining_principal;
    }
    // Sets the value of the remaining principal
    public void setRemainingPrincipal(double rp) {
        remaining_principal = rp;
    }
    // Returns Total Paid
    public double getTotalPaid() {
        return total_paid;
    }
}
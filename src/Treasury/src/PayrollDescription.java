package src.Treasury.src;

public class PayrollDescription {

    private String payrollID;
    private double totalSalary, totalBenefits, totalTaxDeduction, totalRetirementDeduction;
    private int numberOfEmployees;

    PayrollDescription(String payrollID, double totalSalary, double totalBenefits,
                       double totalTaxDeduction, double totalRetirementDeduction,
                       int numberOfEmployees){

        this.payrollID = payrollID;
        this.totalSalary = totalSalary;
        this.totalBenefits = totalBenefits;
        this.totalTaxDeduction = totalTaxDeduction;
        this.totalRetirementDeduction = totalRetirementDeduction;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(String payrollID) {
        this.payrollID = payrollID;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public double getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalBenefits(double totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public double getTotalTaxDeduction() {
        return totalTaxDeduction;
    }

    public void setTotalTaxDeduction(double totalTaxDeduction) {
        this.totalTaxDeduction = totalTaxDeduction;
    }

    public double getTotalRetirementDeduction() {
        return totalRetirementDeduction;
    }

    public void setTotalRetirementDeduction(double totalRetirementDeduction) {
        this.totalRetirementDeduction = totalRetirementDeduction;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}

/**
 * @author Kenny
 */

package src.Treasury.src;

import src.Treasury.src.interfaces.PayrollReportInterface;

public class PayrollReport implements PayrollReportInterface {

	private double basePay, benefitsPay, overtimePay, grossPay, netPay;
	
	public PayrollReport (double basePay, double benefitsPay, double overtimePay,
						  double grossPay, double netPay) {
		
		this.basePay = basePay;
		this.benefitsPay = benefitsPay;
		this.overtimePay = overtimePay;
		this.grossPay = grossPay;
		this.netPay = netPay;
	}
	
	@Override
	public double getTotalBasePay() {
		return basePay;
	}

	@Override
	public double getTotalBenefitsPay() {
		return benefitsPay;
	}

	@Override
	public double getTotalOvertimePay() {
		return overtimePay;
	}

	@Override
	public double getTotalGrossPay() {
		return grossPay;
	}

	@Override
	public double getTotalNetPay() {
		return netPay;
	}
}

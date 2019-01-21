/**
 * 
 */
package atm.src;

import bank.src.BankDatabase;

/**
 * @author USERL
 *
 */
public class TransferFunds extends Transaction{

	private double amount; //amount to be transferred
	
	/**
	 * 
	 */
	public TransferFunds(int senderAccountNo, double amount, int receiverAccountNo, Screen scr, BankDatabase bank) {
		
		super(senderAccountNo, scr, bank);
		
		
	}

	public void execute(){
		
	}
	
}

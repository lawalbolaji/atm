/**
 * 
 */
package atm.src;

import bank.src.BankDatabase;

/**
 * @author USERL
 *
 */
public class BalanceInquiry extends Transaction{
	
	
	/**
	 * 
	 */
	public BalanceInquiry(int accountNumber, Screen screen, BankDatabase bankDatabase) {
		
		super(accountNumber, screen, bankDatabase);
	}
	
	
	@Override
	public void execute(){
		
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		double totalBalance = bankDatabase.getTotalBalance(getAccountNumber());
		
		double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());		
		
		screen.displayMessageLine( "\nBalance Information:" );
		screen.displayMessage( " - Available balance: " );
		screen.displayDollarAmount( availableBalance );
		screen.displayMessage( "\n - Total balance: " );
		screen.displayDollarAmount( totalBalance );
		screen.displayMessageLine("");
	}

}

/**
 * 
 */
package atm.src;

import bank.src.BankDatabase;

/**
 * @author USERL
 *
 */
public class Withdrawal extends Transaction{
	
	//we are assuming user is only allowed to withdraw 
	//whole amounts in multiples of particular notes 
	private int amount;

	private Keypad keypad; //ATM's keypads
	private CashDispenser cashDispenser; //ATM's cashDispenser
	
	private static final int CANCELLED = 6;

	
	/**
	 * 
	 */
	public Withdrawal(int accountNo, 
			Screen scr, Keypad key, CashDispenser cash, BankDatabase bank){
		
		super(accountNo, scr, bank);
		
		keypad = key;
		cashDispenser = cash;
		
	}

	@Override
	public void execute()
	{
		boolean cashDispensed = false;
		double availableBalance;
		
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		do
		{
			amount = displayMenuOfAmounts();
			
			if(amount != CANCELLED){
				
				availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
				
				if( amount <= availableBalance){
					
					if(cashDispenser.isSufficientCashAvailable(amount)){
						
						bankDatabase.debit(getAccountNumber(), amount);
						
						cashDispenser.dispenseCash(amount);
						cashDispensed = true;
						
						screen.displayMessageLine("please take your cash");
					}
					else 
						screen.displayMessageLine("\ninsufficient cas available in the Atm"
								+ "\nplease choose a smaller amount");
						
				}
				else
					screen.displayMessageLine("\nInsufficient funds in your account"
							+ "\nPleasde choose a smaller amount");
			}
			else{
				screen.displayMessageLine("\nCancelling transaction");
				return;
			}
		}
		while(!cashDispensed);
		
		
	}
	
	private int displayMenuOfAmounts(){
		
		int userChoice =0;
		Screen screen = getScreen();
		
		int[] amounts = {0,20,40,60,100,200};
		
		while(userChoice == 0){
			
			screen.displayMessageLine("\nWithdrawal Menu");
			screen.displayMessageLine( "1 - $20" );
			screen.displayMessageLine( "2 - $40" );
			screen.displayMessageLine( "3 - $60" );
			screen.displayMessageLine( "4 - $100" );
			screen.displayMessageLine( "5 - $200" );
			screen.displayMessageLine( "6 - Cancel transaction" );
			screen.displayMessage( "\nChoose a withdrawal amount: " );
			
			int input = keypad.getInput();
			
			switch ( input )
			{
			case 1: // if the user chose a withdrawal amount
			case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
			case 3: // corresponding amount from amounts array
			case 4:
			case 5:
				userChoice = amounts[input]; // save user's choice
				break;
			case CANCELLED: // the user chose to cancel
				userChoice = CANCELLED; // save user's choice
				break;
			default: // the user did not enter a value from 1-6
				screen.displayMessageLine(
					"\nInvalid selection. Try again." );
			} // end switch
		} // end while
		
			
		return userChoice;
		
	}
	
	
}

package atm.src;

import bank.src.BankDatabase;

public class Deposit extends Transaction{
	
	//assuming user can deposit any amount
	private double amount;
	
	private Keypad keypad; //ATM's keypads
	private DepositSlot depositSlot; //ATM's cashDispenser
	private static final int CANCELLED = 0;
	
	public Deposit(int accountNo, Screen scr, Keypad key, DepositSlot dep, BankDatabase bank){
		
		super(accountNo, scr, bank);
		
		keypad = key;
		depositSlot = dep;
	}
	
	@Override
	public void execute(){
		
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		amount = promptForDepositAmount();
		
		if( amount != CANCELLED ){
			
			screen.displayMessageLine("\nPlease insert an envelope containing ");
			screen.displayDollarAmount(amount);
			screen.displayMessageLine(".");
			
			boolean envelopeReceived = depositSlot.isEnvelopeReceived();
			
			if(envelopeReceived){
				
				screen.displayMessage("\nYour envelope has been "
						+ "received.\nNOTE: The money just deposited will not " 
						+"be available until we verify the amount of any " 
						+"enclosed cash and your checks clear.");
				
				bankDatabase.credit(getAccountNumber(), amount);
			}
			else
				screen.displayMessageLine("\nYou did not insert an envelope"
						+ "so, the system is cancelling your transaction");
		}
		else
			screen.displayMessageLine("\ncancelling transaction");
	}
	
	private double promptForDepositAmount(){
		
		Screen screen = getScreen();
		
		screen.displayMessageLine("");
		int input = keypad.getInput();
		
		if(input != CANCELLED){
			
			return (double) input / 100; //return dollar amount of deposit
		}
		else
			return CANCELLED;
	}
	
	

}

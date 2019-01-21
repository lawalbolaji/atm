package atm.src;

import bank.src.BankDatabase;

public class ATM {

	private boolean userAuthenticated;
	private int currentAccountNumber;
	private Screen screen;
	private Keypad keypad;
	private CashDispenser cashDispenser;
	private BankDatabase bankDatabase;
	private DepositSlot depositSlot;
	
	private static final int BALANCE_INQ = 1;
	private static final int WITHDRAWAL = 2;
	private static final int DEPOSIT = 3;
	private static final int EXIT = 4;
	
	
	
	public ATM() {
		
		userAuthenticated = false;
		currentAccountNumber = 0;
		screen = new Screen();
		keypad = new Keypad();
		cashDispenser = new CashDispenser();
		bankDatabase = new BankDatabase();
		depositSlot = new DepositSlot();
		
	}
	
	public void run(){
		
		while(true){
			
			//loop while user is not yet authenticated
			while(!userAuthenticated){
				
				screen.displayMessageLine(""
						+ "\n<<<<<<<<<<<<<<<<<<<WELCOME>>>>>>>>>>>>>>>>>>>\n");
				authenticateUser();
				
			}
			
			//user is now authenticated
			performTransaction();
			
			//reset before next transaction
			userAuthenticated = false;
			currentAccountNumber = 0;
			screen.displayMessageLine("Thank you!");
			
		}
		
		
	}
	
	//attempts to authenticate user against database
	private void authenticateUser(){
		
		screen.displayMessage("Enter your account Number");
		int accountNumber = keypad.getInput();
		screen.displayMessage("Enter your PIN");		
		int PIN = keypad.getInput();
		
		userAuthenticated = 
				bankDatabase.authenticateUser(accountNumber, PIN);
		
		//check if authentication succeeded
		if(userAuthenticated)
			currentAccountNumber = accountNumber;
		else
			screen.displayMessageLine("Invalid PIN, please try again");
	}
	
	private void performTransaction(){
		
		Transaction currentTransaction = null;
		
		boolean userExited = false;
		
		while(!userExited){
			
			int mainMenuSelection = displayMenu();
			
			switch(mainMenuSelection)
			{
			case BALANCE_INQ:
			case WITHDRAWAL:
			case DEPOSIT:
				currentTransaction = createTransaction(mainMenuSelection);
				
				currentTransaction.execute();
				break;
			case EXIT:
				screen.displayMessage("Exiting the system");
				userExited = true;
				break;
			default:
				screen.displayMessageLine("you did not enter a valid selection, please try again");
				break;
			}
		}
	}
	
	private int displayMenu(){
		
		screen.displayMessageLine("\nMainMenu: ");
		screen.displayMessageLine("1 - View my Balance");
		screen.displayMessageLine("2 - Withdraw Cash");
		screen.displayMessageLine("3 - Deposit funds");
		screen.displayMessageLine("4 - Exit");
		
		return keypad.getInput();
	}

	private Transaction createTransaction(int type){
		
		Transaction temp = null;
		
		switch(type)
		{
		case BALANCE_INQ:
			temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
			break;
		case WITHDRAWAL:
			temp = new Withdrawal(currentAccountNumber, 
					screen, keypad, cashDispenser, bankDatabase);
			break;
		case DEPOSIT:
			temp = new Deposit(currentAccountNumber, 
					screen, keypad, depositSlot, bankDatabase);
			break;
		}
		
		return temp;
		
	}
}

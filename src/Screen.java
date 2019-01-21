package atm.src;

/**
 * 
 */

/**
 * @author USERL
 *
 */

public class Screen {

	/**
	 * 
	 */
	
	public void displayMessageLine(String message){
		
		System.out.println(message);
	}

	public void displayMessage(String message){
		
		System.out.print(message);
	}

	public void displayDollarAmount(double amount){
		
		System.out.printf("$%,.2f", amount);
	}
	
}

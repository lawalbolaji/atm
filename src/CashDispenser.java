package atm.src;

/**
 * @author USERL
 *
 */
public class CashDispenser {

	private static final int INITIAL_COUNT = 500;
	private int count;
	
	/**
	 * 
	 */
	public CashDispenser() 
	{
		count = INITIAL_COUNT;
		
	}
	
	public void dispenseCash(int amount){
		
		int billsRequired = amount/20;
		count -= billsRequired;
		
	}
	
	public boolean isSufficientCashAvailable(int amount){
		
		int billsRequired = amount/20; //assume whole number amounts are requested
		
		if(count >= billsRequired){
			return true;
		}
		return false;
	}

}

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RuleEngine 
{
	
	public double execRules(Product product, int quantity, Customer customer, String promoCode)
	{
		double overrideTotal = 0;
		
		System.out.print(product.getProductName() + " | "+ quantity + " | ");
		/**RULE 1: 3 for 2 ult_small**/
		if(product.getId() == 1 && quantity >= 3 && getMonthEnrolled(customer.getDateCreated()) <= 1)
		{	
			int qualified = quantity/3;
			int notQualified = quantity - (qualified*3); 
			overrideTotal = (product.getPrice() * 2 * qualified) + (product.getPrice() * notQualified);
			
			System.out.print("$" + overrideTotal + " |");
			System.out.println(" Availed '3 for 2 deal on Unlimited 1GB Sims' x "+qualified);
			
		}
		/**RULE 2: Unlimited 5GB Bulk**/
		else if(product.getId() == 3 && quantity > 3 && getMonthEnrolled(customer.getDateCreated()) <= 1)
		{
			overrideTotal = (quantity * 39.90);
			System.out.print("$" + overrideTotal + " |");
			System.out.println(" Availed 'Unlimited 5GB Bulk Discount'");
			
		}
		/**RULE 3: free 1 GB Data-pack**/
		else if(product.getId() == 2)
		{
			overrideTotal = (quantity * product.getPrice());
			System.out.println("$" + overrideTotal + " |");
			System.out.println("1GB Data-pack | "+ quantity + " |  FREE  | Free-of-charge with every Unlimited 2GB sold");
			
		}
		/**NO RULE**/
		else
		{
			overrideTotal = (quantity * product.getPrice());
			df2.setRoundingMode(RoundingMode.UP);
			System.out.print("$" + df2.format(overrideTotal) + " |");
			System.out.println(" ");
			overrideTotal = (quantity * product.getPrice());
		}
		
		/**RULE 4: I<3AMAYSIM Promo**/
		if(promoCode.equals("I<3AMAYSIM"))
		{
			overrideTotal = overrideTotal * .90;
		}
		
				
		return overrideTotal;
	}
	
	public int getMonthEnrolled(Date dateCustomer)
	{
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(dateCustomer);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date());

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
		return diffMonth;
	}
	
	DecimalFormat df2 = new DecimalFormat(".##");
}

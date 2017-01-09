import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class ShoppingCart 
{
	List<Item> items;
	int numberOfItems;
	double total;
	Customer customer;
	String promoCode;
	
	public ShoppingCart()
	{
		items = new ArrayList<Item>();
		numberOfItems = 0;
		total = 0;
	}
	
	public synchronized List<Item> getItems() 
	{
		
		return items;
	}
		
	public synchronized void addItem(Product product) 
	{
		boolean newItem = true;
		
		for (Item item : items)
		{
			if (item.getProduct().getProductCode() == product.getProductCode()) 
			{
				newItem = false;
				item.incrementQuantity();
			}
		}
		
		if(newItem)
		{
			Item item = new Item(product);
			items.add(item);
		}
		
	
	}
	
	public synchronized void update(Product product, String quantity) 
	{
		short qty = Short.parseShort(quantity);
		
		if (qty >= 0) 
		{
			Item newItem = null;
			
			for (Item item : items) 
			{
				if (item.getProduct().getProductCode() == product.getProductCode()) 
				{
					if (qty != 0) 
					{
						item.setQuantity(qty);
					}
					else
					{
						newItem = item;
						break;
					}
				}
				
				if (newItem != null) 
				{
					items.remove(item);
				}
			}
		}
	}
	
	
	public synchronized double getTotalAmount() 
	{
		total = 0;
		System.out.println("Product       |Qty| Amount | Comment");
		for (Item item : items) 
		{
			Product product = item.getProduct();
			RuleEngine engine = new RuleEngine();
			double rulePrice = engine.execRules(product, item.getQuantity(), customer, promoCode);
			
			total += rulePrice;
			
		}
		System.out.println("-----------------------------------------------------");
		df2.setRoundingMode(RoundingMode.UP);
		System.out.println("TOTAL : $"+ df2.format(total));
		return total;
	}
	
	public synchronized void clear() 
	{
		items.clear();
		numberOfItems = 0;
		total = 0;
	}

	public Customer getCustomer() 
	{
		return customer;
	}

	public void setCustomer(Customer customer) 
	{
		this.customer = customer;
	}

	public String getPromoCode() 
	{
		return promoCode;
	}

	public void setPromoCode(String promoCode) 
	{
		this.promoCode = promoCode;
	}

	DecimalFormat df2 = new DecimalFormat(".##");
	
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shop 
{

	static Map<String, Product> productList;
	
	public static void main(String[] args) 
	{
		initialize();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter your name: ");
		String username = scanner.next();
		
		System.out.print("Enter membership date (mm/dd/yyyy): ");
		String dateEnrolled = scanner.next();
		
		Customer customer = new Customer();
		
		try 
		{
			customer.setName(username);
			customer.setDateCreated(sdf.parse(dateEnrolled));
		} 
		catch (ParseException e) 
		{
			customer.setDateCreated(null);
		}
		
		String promoCode = "";
		System.out.print("Do you want to use a promo code (yes/no)? ");
		String hasCode = scanner.next();
		
		if(hasCode.equals("yes"))
		{
			System.out.print("Enter promo code: ");
			promoCode = scanner.next();
		}
		
		
		
		System.out.println("");
		System.out.println("-----------------------------------------");
		System.out.println("Amaysim Shopping Cart - Product List");
		System.out.println("-----------------------------------------");
		

		for(Product product: productList.values())
		{
			System.out.println(product.getId() + " | " +product.getProductName() + " | $" +product.getPrice() );
		}
		
		System.out.println("");
		System.out.println("Actions: add | update | clear | check-out | exit");
		System.out.println("");

		
		ShoppingCart cart = new ShoppingCart();
		cart.setCustomer(customer);
		cart.setPromoCode(promoCode);
		
		
		while(true)
		{
			System.out.print("Select action: ");
			String action = scanner.next().trim();
			
			boolean exit = false;
			
			if(action.equals("add") || action.equals("update") || action.equals("clear") || action.equals("check-out") || action.equals("exit"))
			{
				
				String itemID = "";
				String itemQty = "";
				Product product = null;	
				
				switch (action) 
				{
	
					case "add":  
			        	
						System.out.print("Item ID: ");
						itemID = scanner.next();
						
						System.out.print("Quantity: ");
						itemQty = scanner.next();
						
						product = productList.get(itemID);
						
						if(product != null)
						{
							int qty = Integer.parseInt(itemQty);
							if(qty >=1)
							{
								 for(int i=1; i<=qty; i++)
								 {
						            cart.addItem(product);
						         }
							}
						
							System.out.println("*Order added to cart*");
							System.out.println("-------------------");
							System.out.println("");
						}
						else
						{
							System.out.println("*Order not added, product '" +itemID+"' does not exist.");
							System.out.println("-------------------");
							System.out.println("");
						}
						
						break;
						
			        case "update":
			        	
			        	System.out.print("Item ID: ");
						itemID = scanner.next();
						
						System.out.print("Quantity: ");
						itemQty = scanner.next();
						
			        	product = productList.get(itemID);
						
						if(product != null)
						{
							cart.update(product, itemQty);
				        	System.out.println("*Order updated to cart*");
							System.out.println("-------------------");
							System.out.println("");
						}
						else
						{
							System.out.println("*Order not updated, product '" +itemID+"' does not exist.");
							System.out.println("-------------------");
							System.out.println("");
						}
									        				        		        	
			        	break;
						
			        case "clear":
			        	cart.clear();
			        	break;

			            
			        case "check-out":
			        	cart.getTotalAmount();
			        	System.out.println("");
			        	System.out.print("Confirm order (yes/no)?");
			        	
			        	String confirm = scanner.next();
			        				        		
			    		if(confirm.equals("yes"))
			    		{
			    			System.out.println("");
			    			System.out.println("Transaction Completed.");
			    			exit = true;
			    		}
			    		
			    		System.out.println("");
			        	break;
			        
			        case "exit":
			        	cart.clear();
			        	exit = true;
			        	System.out.println("Transaction Cancelled.");
			        	break;
			        default: 
			        	break;
			    }
			}
			else
			{
				System.out.println("This action is not available. Options are 'add', 'update', 'clear' or 'check-out'."); System.out.println("");
			}
			if(exit == true)
				break;
		}

		
	
	}
	
	public static void initialize()
	{
		/**Load List of products**/
		List<String> list = new ArrayList<>();
		productList = new HashMap();
		
		String fileName = "products.properties";
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) 
		{
			list = stream.collect(Collectors.toList());
			int idNo = 0;
			for(String line : list)
			{
				StringTokenizer tokenizer = new StringTokenizer(line, "|");

				Product product = new Product();
				
				while (tokenizer.hasMoreElements()) 
				{
					product.setProductCode(tokenizer.nextElement().toString());
					product.setProductName(tokenizer.nextElement().toString());
					product.setPrice(Double.parseDouble(tokenizer.nextElement().toString()));
					product.setId(++idNo);
				}
				
				productList.put(String.valueOf(product.getId()), product);
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	

	static SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
}

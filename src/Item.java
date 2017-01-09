
public class Item 
{
	private Product product;
	private int quantity;
	
	public Product getProduct() 
	{
		return product;
	}
	
	public void setProduct(Product product) 
	{
		this.product = product;
	}
	
	public int getQuantity() 
	{
		return quantity;
	}
	
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	public void incrementQuantity()
	{
		quantity++;
	}

	public void decrementQuantity() 
	{
		quantity--;
	}
	
	public Item(Product product) 
	{
		this.product = product;
		quantity = 1;
	}
	
}

package datamodel;
import java.util.*;

/**
 * Class of entity type <i>Order</i>.
 * <p>
 * Order represents a contractual relationship with a Customer for purchased (ordered) items.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Order {

    

    /**
     * Unique id, null or "" are invalid values, id can be set only once.
     */
    private String id = null;

    /**
     * Reference to owning Customer, final, never null.
     */
    private final Customer customer;

    /**
     * Date/time the order was created.
     */
    private final Date creationDate;

    /**
     * Items that are ordered as part of this order.
     */
    private final List<OrderItem> items;

    /**
     * Default constructor
     */
    public Order() {
    	this.creationDate  = Calendar.getInstance().getTime();
		this.customer = new Customer();
		this.items = null;
    }
    
    /**
     * Constructor with customer owning the order.
     * @param customer customer as owner of order, customer who placed that order.
     * @throws IllegalArgumentException when customer argument is null or has invalid id.
     */
    public Order(Customer customer) {
    	this.creationDate  = Calendar.getInstance().getTime();
    	this.items = new ArrayList<OrderItem>();
    	this.customer = customer;
		if(customer == null || customer.getId() == null) {
			throw new IllegalArgumentException("customer or Id Null");
		}
    }

    /**
     * Id getter.
     * @return order id, returns {@code null}, if id is unassigned.
     */
    public String getId() {
        if(this.id == null) {
        	return null;
        }
        return this.id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id only valid id (not null or "") updates id attribute on first invocation.
     * @throws IllegalArgumentException if id argument is invalid ({@code id==null} or {@code id==""}).
     * 
     * @return chainable self-reference.
     */
    public Order setId(String id) {
    	if(id == null) {
    		throw new IllegalArgumentException("invalid id (null).");
    	}else if (id == "") {
    		throw new IllegalArgumentException("invalid id (empty).");
    	}
    	if ( this.id == null ) {
            this.id = id;
        } else {
           
        }
        return this;
    }

    /**
     * Customer getter.
     * @return owning customer, cannot be null.
     */
    public Customer getCustomer() {
        if(customer == null) {
        	throw new IllegalArgumentException("customer null");
        }
        return this.customer;
    }

    /**
     * CreationDate getter, returns the time/date when the order was created.
     * @return time/date when order was created as long in ms since 01/01/1970.
     */
    public long getCreationDate() {
       long date = this.creationDate.getTime();
        return date;
    }

    /**
     * CreationDate setter for date/time, which is valid for {@code 01/01/2020 <= datetime <= now() + 1day}.
     * Orders cannot be older than the lower bound and younger than the current datetime (+1day).
     * @param datetime time/date when order was created (in milliseconds since 01/01/1970).
     * @throws IllegalArgumentException if datetime is outside valid range {@code 01/01/2020 <= datetime <= now() + 1day}.
     * @return chainable self-reference.
     */
    @SuppressWarnings("deprecation")
	public Order setCreationDate(long datetime) {
    	this.creationDate.setTime(datetime);
        return this;
    }

    /**
     * Number of items that are part of the order.
     * @return number of ordered items.
     */
    public int itemsCount() {
        return items.size();
    }

    /**
     * Ordered items getter.
     * @return ordered items.
     */
    public Iterable<OrderItem> getItems() {
        return this.items;
    }

    /**
     * Create new item and add to order.
     * @param article article ordered from catalog.
     * @param units units ordered.
     * @throws IllegalArgumentException if article is null or units not a positive {@code units > 0} number.
     * @return chainable self-reference.
     */
    public Order addItem(Article article, int units) {
    	if(article == null || units < 0) {
    		throw new IllegalArgumentException("");
    	}
        items.add(new OrderItem(article, units));
        return this;
    }

    /**
     * Delete i-th item from order, {@code i >= 0 && i < items.size()}, otherwise method has no effect.
     * @param i index of item to delete, only a valid index deletes item.
     */
    public void deleteItem(int i) {
    	if(i >= 0 || i < items.size()) {
        items.remove(i);
    	}
    }

    /**
     * Delete all ordered items.
     */
    public void deleteAllItems() {
        items.removeAll(items);
    }

}
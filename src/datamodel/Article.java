package datamodel;
import java.util.*;

/**
 * Class of entity type <i>Article</i>.
 * <p>
 * Articles can be referenced as ordered items in orders.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Article {

    /**
     * Default constructor
     */
    public Article() {
    }

    /**
     * Unique id, null or "" are invalid values, id can be set only once.
     */
    private String id = null;

    /**
     * Article description, never null, may be empty "".
     */
    private String description = "";

    /**
     * Price in cent per article, negative values are invalid.
     */
    private long unitPrice = 0;

    /**
     * Currency in which price is quoted, EUR is the default currency.
     */
    private Currency currency = Currency.EUR;

    /**
     * Tax rate applicable to article, German regular Value-Added Tax (VAT) is the default.
     */
    private TAX tax = TAX.GER_VAT;

    /**
     * Default constructor.
     */
    public void Article(){
    }

    /**
     * Constructor with description and price.
     * @param description descriptive text for article.
     * @param unitPrice price (in cent) for one unit of the article.
     * @throws IllegalArgumentException when description is null or empty "" or price negative {@code < 0}.
     */
    public Article(String description, long unitPrice) {
        this.description = description;
        this.unitPrice = unitPrice;
        if(description == null) {
        	throw new IllegalArgumentException("description null");
        }else if (description == "") {
        	throw new IllegalArgumentException("description empty");
        }else if(unitPrice < 0) {
        	throw new IllegalArgumentException("price negativ");
        }
    }

    /**
     * Id getter.
     * @return article order id, returns {@code null}, if id is unassigned.
     */
    public String getId() {
    	if(id == null) {
    		return null;
    	}
        return this.id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id only valid id (not null or "") updates id attribute on first invocation.
     * @throws IllegalArgumentException if id argument is invalid ({@code id==null} or {@code id==""}).
     * @return chainable self-reference
     */
    public Article setId(String id) {
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
     * Description getter.
     * @return descriptive text for article
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Description setter.
     * @param description descriptive text for article, only valid description (not null or "") updates attribute.
     * @throws IllegalArgumentException when description is null or empty "". 
     * @return chainable self-reference.
     */
    public Article setDescription(String description) {
    	if(description == null || description == "") {
    		throw new IllegalArgumentException("description null or empty");
    		
    	}
        this.description = description;
        return this;
    }

    /**
     * UnitPrice getter.
     * @return price in cent for one article unit.
     */
    public long getUnitPrice() {
        
        return this.unitPrice;
    }

    /**
     * UnitPrice setter.
     * @param unitPrice price in cent for one article, only valid price ( {@code >= 0} ) updates attribute.
     * @return chainable self-reference.
     */
    public Article setUnitPrice(long unitPrice) {
    	if(unitPrice >= 0) {
    		this.unitPrice = unitPrice;
    	}
        return this;
    }

    /**
     * Currency getter.
     * @return currency in which unitPrice is quoted.
     */
    public Currency getCurrency() {
        
        return currency;
    }

    /**
     * Currency setter.
     * @param currency in which unitPrice is quoted.
     * @throws IllegalArgumentException if currency is null. 
     * @return chainable self-reference.
     */
    public Article setCurrency(Currency currency) {
    	this.currency = currency;
    	if (currency == null) {
    		throw new IllegalArgumentException("currency null");
    	}
        return this;
    }

    /**
     * TAX getter.
     * @return tax rate applicable for article.
     */
    public TAX getTax() {
        return this.tax;
    }

    /**
     * TAX setter.
     * @param tax rate that applies to article.
     * @throws IllegalArgumentException if tax is null.
     * @return chainable self-reference.
     */
    public Article setTax(TAX tax) {
    	if(tax == null) {
    		throw new IllegalArgumentException("tax null");
    	}
    	this.tax = tax;
        return this;
    }

}
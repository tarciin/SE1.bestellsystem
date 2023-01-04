package datamodel;

import java.util.*;

/**
 * Class for entity type <i>Customer</i>.
 * <p>
 * Customer is an individual (a person, not a business) who creates and holds (owns) orders in the system.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Customer {

    /**
     * Unique Customer id attribute, {@code id < 0} is invalid, id can only be set once.
     */
    private long id = -1;

    /**
     * Customer's surname attribute, never null.
     */
    private String lastName = "";

    /**
     * None-surname name parts, never null.
     */
    private String firstName = "";

    /**
     * Customer contact information with multiple contacts.
     */
    private final List<String> contacts = new ArrayList<String>();

    /**
     * Default constructor.
     */
    public Customer() {
    	// TODO implemt here
    }

    /**
     * Constructor with single-String name argument.
     * @param name single-String Customer name, e.g. "Eric Meyer".
     * @throws IllegalArgumentException if name argument is null.
     */
    public Customer(String name) {
    	if(name == null) {
    		throw new IllegalArgumentException("name null.");
    	}else if (name.isEmpty()){
    		throw new IllegalArgumentException("name empty.");
    	}
		splitName(name);
        // TODO implement here
    }

    /**
     * Id getter.
     * @return customer id, returns {@code null}, if id is unassigned.
     */
    public Long getId() {
    	if(id == -1) {
    		return null;
    	}
        return this.id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id value to assign if this.id attribute is still unassigned {@code id < 0} and id argument is valid.
     * @throws IllegalArgumentException if id argument is invalid ({@code id < 0}).
     * @return chainable self-reference.
     */
    public Customer setId(long id) {
    	if(id < 0) {
    		throw new IllegalArgumentException("invalid id (negative).");
    	}
    	if ( this.id == -1 ) {
            this.id = id;
        } else {
           
        }
        return this;
    }

    /**
     * LastName getter.
     * @return value of lastName attribute, never null, mapped to "".
     */
    public String getLastName() {
    	if(this.lastName == null) {
    		return "";
    	}
        return this.lastName;
    }

    /**
     * FirstName getter.
     * @return value of firstName attribute, never null, mapped to "".
     */
    public String getFirstName() {
    	if(this.firstName == null) {
    		return "";
    	}
        return this.firstName;
    }

    /**
     * Setter that splits a single-String name (for example, "Eric Meyer") into first-
     * ("Eric") and lastName ("Meyer") parts and assigns parts to corresponding attributes.
     * @param first value assigned to firstName attribute, null is ignored.
     * @param last value assigned to lastName attribute, null is ignored.
     * @return chainable self-reference.
     */
    public Customer setName(String first, String last) {
        this.firstName = first;
        this.lastName = last;
        return this;
    }

    /**
     * Setter that splits a single-String name (e.g. "Eric Meyer") into first- and
     * lastName parts and assigns parts to corresponding attributes.
     * @param name single-String name to split into first- and lastName parts.
     * @throws IllegalArgumentException if name argument is null.
     * @return chainable self-reference.
     */
    public Customer setName(String name) {
        return splitName(name);
    }

    /**
     * Return number of contacts.
     * @return number of contacts.
     */
    public int contactsCount() {
    	return contacts.size();
    }

    /**
     * Contacts getter (as {@code String[]}).
     * @return contacts (as {@code String[]}).
     */
    public String[ ] getContacts() {
    	String[] contactsList= new String[contacts.size()];
        for(int i = 0; i < contacts.size();i++) {
        	contactsList[i] = contacts.get(i);
        }
        return contactsList;
    }

    /**
     * Add new contact for Customer. Only Valid contacts(not null. ""or dublicates)are added.
     * @param contact  valid contact (not null or "" nor duplicate), invalid contacts are ignored. 
     * @return chainable self-reference.
     * @throws java.lang.IllegalArgumentException if contact argument is null or empty "" String.
     */
    public Customer addContact(String contact) {
    	String contact2 = contact;
    	if(contact == null) {
    		throw new IllegalArgumentException("");
    	}

    	contact = contact.replaceAll("[\",;']", "");
    	contact = contact.trim();

    	if(contact.length() < 6) {
    		throw new IllegalArgumentException("contact less than 6 characters: \"" + contact2 +
    				"\".");
    	}
    	if(contacts.contains(contact)) {
    		return this;
    	}
        contacts.add(contact);
        return this;
    }

    /**
     *  Delete the i-th contact with {@code i >= 0 and i < contactsCount()}, otherwise method has no effect.
     * @param i  index of contact to delete 
     */
    public void deleteContact(int i) {
    	if(i >= contacts.size() || i < 0) {
    		return;
    	}
    	contacts.remove(i);
    }

    /**
     * Delete all contacts.
     */
    public void deleteAllContacts() {
        contacts.removeAll(contacts);
    }

    /**
     * Split single-String name into last- and first name parts.
     * @param name single-String name to split into first- and last name parts.
     * @return chainable self-reference.
     */
    private Customer splitName(String name) {
    	if (name.contains(",")) {
            String lastName = name.substring(0, name.indexOf(","));
            String firstName = name.substring(name.indexOf(",") + 2, name.length());
            return this.setName(firstName, lastName);
        } else if (name.contains(";")) {
            String lastName = name.substring(0, name.indexOf(";"));
            String firstName = name.substring(name.indexOf(";") + 2, name.length());
            return this.setName(firstName, lastName);
        } else if (name.contains(" ")) {
            String lastName = name.substring(name.lastIndexOf(" ") + 1, name.length());
            String firstName = name.substring(0, name.lastIndexOf(" "));
            return this.setName(firstName, lastName);
        } else if (name.contains(".")) {
            String lastName = name.substring(name.indexOf(".") + 1, name.length());
            String firstName = name.substring(0, name.indexOf(".") + 1);
            return this.setName(firstName, lastName);
        }
        return this.setName("", name);
    }

}
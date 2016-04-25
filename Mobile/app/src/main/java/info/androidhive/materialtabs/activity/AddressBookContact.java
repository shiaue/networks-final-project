package info.androidhive.materialtabs.activity;

import java.util.LinkedList;

/**
 * Created by e on 2/20/16.
 * Stringify all the data
 * Including phone number
 * An array of contacts
 */
public class AddressBookContact {
    private long id; // in JSON no quotes around int
    private String name;
    private LinkedList<String> emails = new LinkedList<>();
    private LinkedList<String> phones = new LinkedList<>();



    AddressBookContact(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String print() {
        return "AddressBookContact {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emails=" + emails +
                ", phones=" + phones +
                '}';
    }

    @Override
    public String toString()    {
        String s = id +", " + name +", " + emails +", " + phones +";";
        return s;
    }

    public void addEmail(String address) {
        if (emails != null) {
            emails.add(address);
        }
    }

    public void addPhone(String number) {
        if (phones != null) {
            phones.add(number);
        }
    }

    public String getId()   {
        return Long.toString(this.id);
    }

    public String getPhone()   {
//        return this.phones.toString();
        String sanitize = null;
        if(this.phones.peekFirst() != null) {
            sanitize = phones.peekFirst().toString();
            sanitize = sanitize.replaceAll("[^0-9]", "");
            // removes US Country Code +1
            if(sanitize.length() > 10)
                sanitize = sanitize.substring(1);
            return sanitize;
        }
      return "no #";
    }

    public String getEmail()   {
        if(this.emails.peekFirst() != null) {
            return emails.peekFirst().toString();
        }
        return "no email";
    }

    public String getName() {
        return this.name;
    }

}

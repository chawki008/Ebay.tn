package beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {

	/**
	 * 
	 */
	private static long serialVersionUID = 1L;
	private int id ; 
	private String first_name;
        private String last_name;
	private String password;
	private Timestamp dateInscription;
	private String email ;
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public User() {
		

	}



	public Timestamp getDateInscription() {
		return dateInscription;
	}



	public void setDateInscription(Timestamp dateInscription) {
		this.dateInscription = dateInscription;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
	

}

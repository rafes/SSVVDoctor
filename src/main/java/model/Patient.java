package model;

public class Patient {
	private String Patient_ID;
	private String Name;
	private String cnp;
	private String address;
	private int consNum;
	
	/** Constructors */
	
	public Patient() {};
	
	public Patient(String Name, String cnp, String address)
	{
			this.Patient_ID = cnp;
			this.Name = Name;
			this.cnp = cnp;
			this.address = address;	
			this.consNum = 0;
	}
	
	/** Getters and setters */
	public String getPatient_ID() {
		return Patient_ID;
	}
	public void setPatient_ID(String patient_ID) {
		Patient_ID = patient_ID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCnp() {
		return cnp;
	}
	public void setCnp(String sSN) {
		cnp = sSN;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setConsNum(int num)
	{
		this.consNum = num;
	}
	public int getConsNum()
	{
		return this.consNum;
	}
	
	/** Others */
	public String toString() {
	    return Name + "," + cnp + "," +address;
	//	return Name + "," + consNum;
	}
}

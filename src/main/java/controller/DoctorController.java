package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ConsultationException;
import exceptions.PatientException;
import repository.Repository;
import validator.PatientValidation;
import model.Consultation;
import model.Patient;

public class DoctorController {

    private List<Patient> PatientList;
    private List<Consultation> ConsultationList;
    private Repository rep;

    /**
     * Constructors
     */

    public DoctorController(Repository rep) {

        PatientList=rep.GetPatients();
        ConsultationList=rep.GetConsultations();
        this.rep = rep;
    }

    /**
     * Getters
     */
    public List<Patient> getPatientList() {
        return rep.GetPatients();
    }

    public List<Consultation> getConsultationList() {
        return rep.GetConsultations();
    }

    public void setConsulationList(List<Consultation> consultationList) {
        ConsultationList = consultationList;
    }

    public Patient getPatientBySSN(String SSN) {
        for (int i = 0; i < PatientList.size(); i++) {
            if (PatientList.get(i).getCnp().equals(SSN))
                return PatientList.get(i) ;
        }

        return null;
    }

    public int getConsByID(String ID) {
        for (int i = 0; i < ConsultationList.size(); i++) {
            if (ConsultationList.get(i).getConsID().compareTo(ID) == 0) {
                /*
				 * System.out.println("I proud to have found " + ID + " here: "
				 * + i); System.out.println("Proof : " +
				 * ConsultationList.get(i).toString());
				 */
                return i - 1;
            }
        }

        return -1;
    }

    public Repository getRepository() {
        return rep;
    }

    /**
     * Others
     */
    public void addPatient(Patient p) throws PatientException {
        rep.addPatient(p);
        try {
            System.out.println("Saving to file");
            rep.savePatientToFile(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addConsultation(Consultation c)throws ConsultationException,PatientException
    {
        rep.addConsultation(c.getConsID(),c.getPatientSSN(),c.getDiag(),c.getMeds(),c.getConsultation_date());
        try {
            rep.saveConsultationToFile(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adding of a new consultation for a patient (consultation date,
    // diagnostic, prescription drugs)

    public void addConsultation(String consID, String patientSSN, String diag,
                                List<String> meds, String date) throws ConsultationException,PatientException {
        rep.addConsultation(consID,patientSSN,diag,meds,date);
    }

    public void addPatient(String name,String cnp,String address)throws PatientException
    {
        rep.addPatient(new Patient(name,cnp,address));
    }


    public ArrayList<Patient> getPatientsWithDisease(String disease) throws PatientException {
        ArrayList<Patient> filteredPatients=new ArrayList<Patient>();
        if(disease==null)
        {
            throw new PatientException("Empty disease");
        }
        for(Consultation c: getConsultationList())
        {
            if(c.getDiag().compareTo(disease)==0)
            {
                Patient p=getPatientBySSN(c.getPatientSSN());
                if(p!=null) {
                    filteredPatients.add(p);
                }
                else{
                    throw new PatientException("No patient with this cnp ! Invalid consultation!");
                }
            }
        }
        return filteredPatients;
    }

	/*
	 * For debugging purposes public void printList() { for (int i = 0; i <
	 * PatientList.size(); i++) {
	 * System.out.println(PatientList.get(i).toString()); } }
	 */

}

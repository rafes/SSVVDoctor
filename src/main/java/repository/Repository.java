package repository;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import validator.PatientValidation;


public class Repository {

    private String patientsFile; // file with patients (corrected)
    private String consultationsFile; // file with consultations

    private ArrayList<Consultation> consultationList;
    private ArrayList<Patient> patientList;

    public Repository(String patients, String consultations) {
        this.patientsFile = patients;
        this.consultationsFile = consultations;
        consultationList=getConsultationList();
        patientList=getPatientList();
    }

    public Repository() {
        consultationList = new ArrayList<Consultation>();
        patientList = new ArrayList<Patient>();
    }

    public void addPatient(Patient p) throws PatientException {
        if (p.getName() != null && p.getCnp() != null && p.getAddress() != null) {
            PatientValidation.nameValidate(p.getName());
            PatientValidation.ssnValidate(p.getCnp());
            PatientValidation.addressValidate(p.getAddress());
        } else {
            throw new PatientException("There cannot be any null fields in a patient's information");
        }
        patientList.add(p);
    }

    public ArrayList<Patient> GetPatients()
    {
        return patientList;
    }

    public ArrayList<Consultation> GetConsultations()
    {
        return consultationList;
    }

    public void addConsultation(String consID, String cnp, String diag, List<String> meds, String date)
            throws ConsultationException, PatientException {
        if (meds == null)
            throw new ConsultationException("Medication string is null");

        if (consID != null && cnp != null && diag != null && meds.size() != 0 && this.getConsultationById(consID) == -1) {
            if (patientExists(cnp)) {
                Consultation c = new Consultation(consID, cnp, diag, meds, date);
                consultationList.add(c);
            } else {
                throw new PatientException("Inexistent patient");
            }
        } else {
            throw new ConsultationException("invalid arguments");
        }
    }

    boolean patientExists(String cnp) {
        for (int i = 0; i < this.patientList.size(); i++) {
            if (this.patientList.get(i).getCnp().equals(cnp)) {
                return true;
            }
        }
        return false;
    }

    int getConsultationById(String id) {
        for (int i = 0; i < this.consultationList.size(); i++) {
            if (this.consultationList.get(i).getConsID().equals(id)) {
                return 1;
            }
        }
        return -1;
    }

    public String[] getPatientsFromFile() throws IOException {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(patientsFile));
        while ((in.readLine()) != null) {
            n++;
        }
        in.close();

        String[] la = new String[n];
        String s = new String();
        int i = 0;
        in = new BufferedReader(new FileReader(patientsFile));
        while ((s = in.readLine()) != null) {
            la[i] = s;
            i++;
        }
        in.close();
        return la;
    }

    public String[] getConsultationsFromFile() throws IOException {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(consultationsFile));
        while ((in.readLine()) != null) {
            n++;
        }
        in.close();

        String[] la = new String[n];
        String s = new String();
        int i = 0;
        in = new BufferedReader(new FileReader(consultationsFile));
        while ((s = in.readLine()) != null) {
            la[i] = s;
            i++;
        }
        in.close();
        return la;
    }

    private ArrayList<Patient> getPatientList() {
        ArrayList<Patient> lp = new ArrayList<Patient>();
        try {
            String[] tokens = getPatientsFromFile();

            String tok = new String();
            String[] pat;
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                pat = tok.split(",");
                lp.add(new Patient(pat[1], pat[0], pat[2]));
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    private ArrayList<Consultation> getConsultationList() {

        ArrayList<Consultation> lp = new ArrayList<Consultation>();
        try {
            String tok = new String();
            String[] cons;
            String[] tokens=getConsultationsFromFile();
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                cons = tok.split(",");
                lp.add(new Consultation(cons[0], cons[1], cons[2],cons[3]));
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    public void savePatientToFile(Patient p) throws IOException        // save to file
    {
        FileWriter fw = new FileWriter(patientsFile, true); //the true will append the new data
        fw.write(p.toString() + "\n");//appends the string to the file
        fw.close();

    }

    public void saveConsultationToFile(Consultation c) throws IOException        // save to file
    {
        FileWriter fw = new FileWriter(consultationsFile, true);
        System.out.println("Writing to file the consultattion");
        fw.write(c.toString() + "\n");//appends the string to the file
        fw.close();
    }
}

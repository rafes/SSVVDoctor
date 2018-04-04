package repository;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        consultationList = new ArrayList<Consultation>();
        patientList = new ArrayList<Patient>();
    }

    public Repository() {
        consultationList = new ArrayList<Consultation>();
        patientList = new ArrayList<Patient>();
    }

    public void cleanFiles() {
        FileWriter fw;
        try {
            fw = new FileWriter(patientsFile);
            PrintWriter out = new PrintWriter(fw);
            out.print("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter fwc;
        try {
            fwc = new FileWriter(consultationsFile);
            PrintWriter out = new PrintWriter(fwc);
            out.print("");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void addConsultation(String consID, String cnp, String diag, List<String> meds, String date)
            throws ConsultationException {
        if (meds == null)
            throw new ConsultationException("Medication string is null");

        if (consID != null && cnp != null && diag != null && meds.size() != 0
                && patientExists(cnp) && this.getConsultationById(consID) == -1) {
            Consultation c = new Consultation(consID, cnp, diag, meds, date);
            try {
                this.saveConsultationToFile(c);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Patient p = new Patient();
            //p = this.getPatientList().get(this.getPatientBySSN(c.getPatientSSN()));
            // p.setConsNum(p.getConsNum() + 1);
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

    public List<Patient> getPatientList() {
        List<Patient> lp = new ArrayList<Patient>();
        try {
            String[] tokens = getPatientsFromFile();

            String tok = new String();
            String[] pat;
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                pat = tok.split(",");
                lp.add(new Patient(pat[0], pat[1], pat[2]));
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    public List<Consultation> getConsultationList() {
        List<Consultation> lp = new ArrayList<Consultation>();
        try {
            String[] tokens = getConsultationsFromFile();


            String tok = new String();
            String[] cons;
            String[] meds;
            List<String> med = new ArrayList<String>();
            int i = 0;
            while (i < tokens.length) {
                tok = tokens[i];
                cons = tok.split(",");
                meds = cons[3].split("\\+");
                Consultation consultation = new Consultation(cons[0], cons[1], cons[2], med, cons[4]);
                for (int j = 0; j < meds.length - 1; j++) {
                    consultation.getMeds().add(meds[j]);
                }
                lp.add(consultation);
                i = i + 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lp;
    }

    public void savePatientToFile(Patient p) throws IOException        // save to file
    {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(patientsFile));
        while ((in.readLine()) != null)
            n++;
        in.close();
        String[] sl = new String[n];
        String str;
        int i = 0;
        in = new BufferedReader(new FileReader(patientsFile));
        while ((str = in.readLine()) != null) {
            sl[i] = str;
            i++;
        }
        in.close(); // append
        FileWriter fw = new FileWriter(patientsFile);
        PrintWriter out = new PrintWriter(fw);
        for (i = 1; i < sl.length - 1; i++)
            out.println(sl[i]);
        out.println(p.toString());
        out.close();
    }

    public void saveConsultationToFile(Consultation c) throws IOException        // save to file
    {
        int n = 0;
        BufferedReader in = new BufferedReader(new FileReader(consultationsFile));
        while ((in.readLine()) != null)
            n++;
        in.close();
        String[] sl = new String[n];
        String str;
        int i = 0;
        in = new BufferedReader(new FileReader(consultationsFile));
        while ((str = in.readLine()) != null) {
            sl[i] = str;
            i++;
        }
        in.close(); // append
        FileWriter fw = new FileWriter(consultationsFile);
        PrintWriter out = new PrintWriter(fw);
        for (i = 0; i < sl.length - 1; i++)
            out.println(sl[i]);
        out.println(c.toString());
        out.close();
    }
}

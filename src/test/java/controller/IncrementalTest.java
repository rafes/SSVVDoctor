package controller;

import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Sabina on 14/05/2018.
 */
public class IncrementalTest {
    private Patient p1,p2,p3;
    private Consultation c1,c2;
    private Repository repo,repo1;
    private DoctorController ctrl,ctrl1;
    java.util.List<String> meds;

    @Before
    public void setUp() throws Exception {
        p1 = new Patient("Ana Alexa", "2960512070043", "Calea Nationala");
        p2 = new Patient(null, "1940503245234", "Calea Nationala");
        p3=new Patient("Sabina Alexa","2980512070043","Calea 1");
        repo = new Repository();
        repo1=new Repository();
        ctrl=new DoctorController(repo);
        ctrl1=new DoctorController(repo1);
        meds=new ArrayList<String>();
        meds.add("Paracetamol");
    }

    @Test(expected = PatientException.class)
    public void addPatient() throws Exception {
        System.out.println(repo);
        System.out.println(ctrl);
        int size=ctrl.getPatientList().size();
        assertEquals(size,0);
        ctrl.addPatient(p1.getName(),p1.getCnp(),p1.getAddress());
        assertEquals(ctrl.getPatientList().size(),1);
        ctrl.addPatient(p2);
        assertEquals(ctrl.getPatientList().size(),1);
    }

    @Test
    public void addConsultation() throws Exception {
        assertEquals(ctrl1.getPatientList().size(),0);
        ctrl1.addPatient(p3.getName(),p3.getCnp(),p3.getAddress());
        assertEquals(ctrl1.getPatientList().size(),1);
        assertEquals( ctrl1.getConsultationList().size(),0);
        ctrl1.addConsultation("1",p3.getCnp(),"gripa",meds,"10/05/2018");
    }

    @Test
    public void getPatientsWithDisease() throws Exception {
        assertEquals(ctrl.getPatientList().size(),0);
        ctrl.addPatient(p1.getName(),p1.getCnp(),p1.getAddress());
        assertEquals(ctrl.getPatientList().size(),1);
        ctrl.addPatient(p3.getName(),p3.getCnp(),p3.getAddress());
        assertEquals(ctrl.getPatientList().size(),2);
        assertEquals(ctrl.getConsultationList().size(),0);
        ctrl.addConsultation("1",p1.getCnp(),"gripa",meds,"09/05/2018");
        ctrl.addConsultation("2",p3.getCnp(),"gripa",meds,"10/05/2018");
        assertEquals(ctrl.getConsultationList().size(),2);
        ArrayList<Patient> pts=ctrl.getPatientsWithDisease("gripa");
        assertEquals(pts.size(),2);
        assertEquals(pts.get(0).getCnp(),p1.getCnp());
        assertEquals(pts.get(1).getCnp(),p3.getCnp());
    }

}
package controller;

import model.Consultation;
import model.Patient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sabina on 18/04/2018.
 */
public class DoctorControllerTest {

    List<String> meds=new ArrayList<String>();
    Patient p1=new Patient("Alexa Ana", "1940503245234", "Calea Nationala");
    Consultation c1=new Consultation("1","1940503245234","boala",meds,"");
    @Before
    public void setUp() throws Exception {
        meds.add("Calciu");
    }

    @Test
    public void addPatient() throws Exception {

    }

    @Test
    public void addConsultation() throws Exception {

    }

    @Test
    public void getPatientsWithDisease() throws Exception {

    }

}
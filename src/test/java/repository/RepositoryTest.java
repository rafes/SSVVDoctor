package repository;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sabina on 28/03/2018.
 */
public class RepositoryTest {

    private Patient p1, p2, p3, p4,p5;
    private Repository repository;
    List<String> meds;
    String consId=null;

    @Before
    public void setUp() throws Exception {
        p1 = new Patient("", "1940503245234", "Calea Nationala");
        p2 = new Patient(null, "1940503245234", "Calea Nationala");
        p3 = new Patient("A", "1940503245234", "Calea Nationala");
        p4 = new Patient();
        p5=new Patient("Ana Alexa","123","Calea Nationala");
        repository = new Repository();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = PatientException.class)
    public void testAddPatientNullName() throws PatientException {
        /*Adding a patient with a null name -should throw PatientException -bug fixed(Patient array list was not initialised)*/
        repository.addPatient(p2);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientEmptyName() throws PatientException {
        repository.addPatient(p1);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientWithInvalidName() throws PatientException {
        p1.setName("Ana3 Ale4a&");
        repository.addPatient(p1);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientNameTooShort() throws PatientException {
        /*Modified in patient validator that the name is at least 2 characters long*/
        repository.addPatient(p3);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientCnpTooShort() throws PatientException {
        repository.addPatient(p5);
    }

    @Test()
    public void testAddPatientCnpInvalidNumber()
    {
        p5.setCnp("1s23120545676");
        try {
            repository.addPatient(p5);
        }catch (PatientException x)
        {
            assertEquals(x.getMessage(),"CNP must contain only digits!");
        }
    }

    @Test
    public void testAddPatientCnpInvalidFirstDigit()
    {
        p5.setCnp("5223120545676");
        try {
            repository.addPatient(p5);
        }catch (PatientException x)
        {
            assertEquals(x.getMessage(),"CNP must start with 1 or  2");
        }
    }

    @Test
    public void testAddPatientCnpInvalidMonth()
    {
        p5.setCnp("1223120545676");
        try {
            repository.addPatient(p5);
        }catch (PatientException x)
        {
            assertEquals(x.getMessage(),"CNP month cannot be larger than 12");
        }
    }

    @Test
    public void testAddPatientCnpInvalidDay()
    {
        p5.setCnp("1220134545676");
        try {
            repository.addPatient(p5);
        }catch (PatientException x)
        {
            assertEquals(x.getMessage(),"CNP day cannot be larger than 31");
        }
    }

    @Test
    public void testAddPatientCnpTooManyDigits()
    {
        p5.setCnp("122011454567678");
        try {
            repository.addPatient(p5);
        }catch (PatientException x)
        {
            assertEquals(x.getMessage(),"CNP must have the length 13");
        }
    }

    @Test(expected = PatientException.class)
    public void testAddPatientWithInvalidAddress() throws PatientException
    {
        p5.setCnp("1940503245234");
        p5.setAddress("");
        repository.addPatient(p5);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientWithNullAddress() throws PatientException
    {
        p5.setCnp("1940503245234");
        p5.setAddress(null);
        repository.addPatient(p5);
    }

    @Test(expected = ConsultationException.class)
    public void testAddConsulationNullMeds() throws ConsultationException {
        assertEquals(meds,null);
        repository.addConsultation("1","123040533489","boala",meds,"");
    }

    @Test(expected = ConsultationException.class)
    public void addConsulationWithNullId()throws ConsultationException
    {
        assertEquals(consId,null);
        repository.addConsultation(consId,"123040533489","boala",meds,"");
    }





}
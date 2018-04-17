package repository;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
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
    String consId=null,consCnp=null,consDisease=null,consDiag=null;

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
    public void testAddConsulationNullMeds() throws ConsultationException,PatientException {
        assertEquals(meds,null);
        repository.addConsultation("1","123040533489","boala",meds,"");
    }

    @Test(expected = ConsultationException.class)
    public void addConsulationWithNullId()throws ConsultationException,PatientException
    {
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertEquals(consCnp,null);
        assertNotEquals("boala",null);
        assertEquals(consId,null);
        repository.addConsultation(consId,"123040533489","boala",meds,"");
    }

    @Test(expected = ConsultationException.class)
    public void addConsultationWithNullCnp()throws ConsultationException,PatientException
    {
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertEquals(consCnp,null);
        assertNotEquals("boala",null);
        consId="1";
        repository.addConsultation(consId,consCnp,"boala",meds,"");
    }
    @Test(expected = ConsultationException.class)
    public void addConsulationWithNullDiagnostic()throws ConsultationException,PatientException
    {
        consCnp="1980305090809";
        consId="1";
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertNotEquals(consId,null);
        assertNotEquals(consCnp,null);
        assertEquals(consDiag,null);
        repository.addConsultation("2",consCnp,consDiag,meds,"");
    }

    @Test(expected=PatientException.class)
    public void addConsulationWithInexistentPatient()throws ConsultationException,PatientException
    {
        consCnp="1980305090809";
        consId="3";
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        consDiag="Prost";
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertNotEquals(consId,null);
        assertNotEquals(consCnp,null);
        assertNotEquals(consDiag,null);
        assertEquals(repository.patientExists(consCnp),false);
        repository.addConsultation(consId,consCnp,consDiag,meds,"03/04/2018");
    }

    @Test(expected= ConsultationException.class)
    public void addConsulationWithIncorrectId()throws ConsultationException
    {

        consCnp="1940503245234";
        consId="3";
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        consDiag="Prost";
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertNotEquals(consId,null);
        assertNotEquals(consCnp,null);
        assertNotEquals(consDiag,null);

        Patient p = new Patient("Ana Alexa", "1940503245234", "Calea Nationala");
        try {
            repository.addPatient(p);
            repository.addConsultation(consId,consCnp,consDiag,meds,"03/04/2018");
            assertEquals(repository.patientExists(consCnp),true);
            repository.addConsultation(consId,consCnp,consDiag,meds,"03/04/2018");
        } catch (PatientException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddCorrecctConsulation()
    {
        consCnp="1940503245234";
        consId="3";
        meds=new ArrayList<String>() ;
        meds.add("Cloroform");
        consDiag="Prost";
        assertNotEquals(meds.size(),0);
        assertNotEquals(meds,null);
        assertNotEquals(consId,null);
        assertNotEquals(consCnp,null);
        assertNotEquals(consDiag,null);
        Patient p = new Patient("Ana Alexa", "1940503245234", "Calea Nationala");
        try {
            repository.addPatient(p);
            assertEquals(repository.patientExists(consCnp),true);
            repository.addConsultation(consId,consCnp,consDiag,meds,"03/04/2018");
        } catch (PatientException e) {
            e.printStackTrace();
        } catch (ConsultationException e) {
            e.printStackTrace();
        }
    }






}
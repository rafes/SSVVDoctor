package validator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import exceptions.PatientException;

public class PatientValidation {
    public static void nameValidate(String name) throws PatientException {
        if (name.length() == 0) {
            throw new PatientException("Name of the patient cannot be empty!");
        }
        if(name.length()<3)
        {
            throw new PatientException("Name of the patient cannot be this short!");
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z -]+$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.find()) {
            throw new PatientException("Name of the patient cannot contain any other characters besides letters!");
        }


    }

    public static void ssnValidate(String ssn) throws PatientException {
        if (ssn.length() != 13) {
            //System.out.println("cnp="+ ssn+" length is ="+ssn.length());
            throw new PatientException("CNP must have the length 13");
        }
        if (!Character.toString(ssn.charAt(0)).equals("1") && !Character.toString(ssn.charAt(0)).equals("2")) {
            throw new PatientException("CNP must start with 1 or  2");
        }

        Pattern pattern = Pattern.compile("\\d+");
        if (!pattern.matcher(ssn).matches()) {
            throw new PatientException("CNP must contain only digits!");
        }

        String month = Character.toString(ssn.charAt(3)) + Character.toString(ssn.charAt(4));
        Integer i = Integer.parseInt(month);
        if (i > 12) {
            throw new PatientException("CNP month cannot be larger than 12");
        }

        String day = Character.toString(ssn.charAt(5)) + Character.toString(ssn.charAt(6));
        Integer ii = Integer.parseInt(day);
        if (ii > 31) {
            throw new PatientException("CNP day cannot be larger than 31");
        }
    }

    public static void addressValidate(String address) throws PatientException {
        if (address.length() == 0) {
            throw new PatientException("Address cannot be empty!");
        }
    }
}

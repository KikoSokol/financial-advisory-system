package sk.stu.fei.uim.bp.application.validarors;

import sk.stu.fei.uim.bp.application.validarors.anotations.PersonalNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;

public class PersonalNumberValidator implements ConstraintValidator<PersonalNumber,String>
{

    @Override
    public void initialize(PersonalNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        value = value.trim();
        String[] parts = value.split("/");

        if(parts.length != 2)
            return false;

        if(parts[0].length() != 6)
            return false;

        if(parts[1].length() != 4)
            return false;

        String firstPart = parts[0];
        String secondParts = parts[1];
        String withoutSlash = firstPart.concat(secondParts);

        long checkNumber;
        try {
            checkNumber = Long.parseLong(withoutSlash);
        }catch (NumberFormatException numberFormatException)
        {
            return false;
        }

        if(checkNumber % 11 != 0)
            return false;

        if(!isMonthCorrect(firstPart))
            return false;

        if(!isCorrectDate(firstPart))
            return false;

        return true;
    }

    public static boolean isValid(String value)
    {
        value = value.trim();
        String[] parts = value.split("/");

        if(parts.length != 2)
            return false;

        if(parts[0].length() != 6)
            return false;

        if(parts[1].length() != 4)
            return false;

        String firstPart = parts[0];
        String secondParts = parts[1];
        String withoutSlash = firstPart.concat(secondParts);

        long checkNumber;
        try {
            checkNumber = Long.parseLong(withoutSlash);
        }catch (NumberFormatException numberFormatException)
        {
            return false;
        }

        if(checkNumber % 11 != 0)
            return false;

        if(!isMonthCorrect(firstPart))
            return false;

        if(!isCorrectDate(firstPart))
            return false;

        return true;
    }

    private static boolean isMonthCorrect(String partsWithMonth)
    {
        int month = getPart(partsWithMonth,2);

        return (month >= 1 && month <= 12) || (month >= 51 && month <= 62);
    }

    private static boolean isCorrectDate(String firstPart)
    {
        int day = getPart(firstPart,3);
        int month = getPart(firstPart,2);
        int year = getPart(firstPart,1);

        if(month > 50)
        {
            month = month - 50;
        }

        try {
            LocalDate localDate = LocalDate.of(year,month,day);
        }
        catch (DateTimeException dateTimeException)
        {
            return false;
        }
        return true;

    }

    private static int getPart(String s,int witchPart)
    {
        String text = "";
        if(witchPart == 1)
        {
            text = s.substring(0,2);
        }
        else if(witchPart == 2)
        {
            text = s.substring(2,4);
        }
        else if(witchPart == 3)
        {
            text = s.substring(4,6);
        }

        return Integer.parseInt(text);
    }
}

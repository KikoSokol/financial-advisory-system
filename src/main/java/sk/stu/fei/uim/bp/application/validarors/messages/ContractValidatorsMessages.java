package sk.stu.fei.uim.bp.application.validarors.messages;

public class ContractValidatorsMessages
{
    public static String CONTRACT_NUMBER_NOT_BLANK_MESSAGE = "Číslo zmluvy je povinný údaj";
    public static String CONTRACT_CURRENT_VERSION_NOT_NULL = "Zmluva musí mať verziu";

    public static String CONTRACT_DOCUMENT_OWNER_NOT_NULL_MESSAGE = "Vlastník zmluvy je povinný údaj";
    public static String CONTRACT_DOCUMENT_DATE_OF_START_NOT_NULL_MESSAGE = "Dátum začiatku platnosti je povinný údaj";
    public static String CONTRACT_DOCUMENT_DATE_OF_START_BEFORE_ANNIVERSARY_DATE_MESSAGE = "Dátum začiatku platnosti musí byť pred výročím poistenia";

//    public static String INSURANCE_INSURED_NOT_NULL_MESSAGE = "Poistený je povinné pole";
    public static String INSURANCE_ANNIVERSARY_DATE_NOT_NULL_MESSAGE = "Výročie poistenia je povinný údaj";
    public static String INSURANCE_ANNIVERSARY_DATE_FUTURE_MESSAGE = "Výročie poistenia môže byť iba v budúcnosti";
    public static String INSURANCE_ANNIVERSARY_DATE_AFTER_DATE_OF_START_MESSAGE = "Výročie poistenia môže musí byť po začiatku platnosti poistenia";


    public static String INSURANCE_PAYMENT_NOT_NULL_MESSAGE = "Platba je povinný údaj";
    public static String INSURANCE_PAYMENT_POSITIVE_OR_ZERO_MESSAGE = "Platba môže byť len kladné číslo alebo 0";
    public static String INSURANCE_PAYMENT_FREQUENCY_NOT_NULL_MESSAGE = "Frekvencia platby je povinné pole";

    public static String VEHICLE_INSURANCE_NUMBER_OF_VEHICLE_NOT_NULL_MESSAGE = "ŠPZ auta je povinný údaj";

    public static String CONTRACT_PRODUCT_LIFE_INSURANCE_MEESAGE = "Životné poistenie musí mať produkt typu životné poistenie";
    public static String CONTRACT_PRODUCT_NON_LIFE_INSURANCE_MEESAGE = "Neživotné poistenie musí mať produkt typu neživotné poistenie";
    public static String CONTRACT_PRODUCT_VEHICLE_INSURANCE_MEESAGE = "Poistenie auta musí mať produkt typu poistenie auta";


    public static String CONTRACT_OWNER_NOT_NULL_MESSAGE = "Majiteľ zmluvy nebol zadaný. Zadajte majiteľa zmluvy";
    public static String INSURANCE_INSURED_NOT_NULL_MESSAGE = "Nebola zadaná poistená osoba. Zadajte poistenú osobu";
}

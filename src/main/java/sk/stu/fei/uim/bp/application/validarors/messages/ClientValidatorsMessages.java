package sk.stu.fei.uim.bp.application.validarors.messages;

public class ClientValidatorsMessages
{
    public static String STREET_MESSAGE_NOT_BLANK =  "Ulica je povinné pole";

    public static String NUMBER_OF_HOUSE_MESSAGE_NOT_BLANK =  "Číslo domu je povinné pole";
    public static String NUMBER_OF_HOUSE_MESSAGE_FORMAT =  "Číslo domu muí byť v správnom tvare";

    public static String POSTAL_CODE_MESSAGE_NOT_BLANK =  "PSČ je povinné pole";
    public static String POSTAL_CODE_MESSAGE_FORMAT =  "PSČ musí byť v správnom tvare";

    public static String CITY_MESSAGE_NOT_BLANK =  "Mesto je povinné pole";
    public static String CITY_MESSAGE_FORMAT=  "Mesto musí obsahovať iba platné znaky";

    public static String STATE_MESSAGE_NOT_BLANK =  "Krajina je povinné pole";
    public static String STATE_MESSAGE_FORMAT =  "Krajina musí obsahovať iba platné znaky";

    public static String ICO_MESSAGE_NOT_BLANK =  "IČO je povinné pole";
    public static String ICO_MESSAGE_FORMAT =  "IČO musí byť 8 miestý číselný kód";

    public static String DIC_MESSAGE_NOT_BLANK =  "DIČ je povinné pole";
    public static String DIC_MESSAGE_FORMAT =  "DIČ musí byť 10 miestný číselný kód";

    public static String DIC_DPH_MESSAGE_NOT_BLANK =  "DIČ DPH je povinné pole";
    public static String DIC_DPH_MESSAGE_FORMAT =  "DIČ DPH musí byť v tvare SKxxxxxxxxxx (x je číslo)";

    public static String BUSINESS_NAME_MESSAGE_NOT_BLANK =  "Obchodné meno je povinné pole";
    public static String BUSINESS_OBJECT_MESSAGE_NOT_BLANK =  "Hlavný predmet podnikania je povinné pole";


    public static String MANAGER_LIST_EMPTY =  "Spoločnosť musí mať minimálne 1 konateľa";

    public static String FIRST_NAME_MESSAGE_NOT_BLANK =  "Meno je povinné pole";
    public static String FIRST_NAME_MESSAGE_FORMAT =  "Meno musí obsahovať iba platné znaky";

    public static String SURNAME_MESSAGE_NOT_BLANK =  "Priezvisko je ovinné pole";
    public static String SURNAME_MESSAGE_FORMAT =  "Priezvisko musí obsahovať iba platné znaky";

    public static String EMAIL_MESSAGE_NOT_BLANK =  "Email je povinné pole";
    public static String EMAIL_MESSAGE_FORMAT =  "Email musí mať správny tvar";

    public static String PHONE_MESSAGE_NOT_BLANK =  "Telefón je povinné pole";
    public static String PHONE_MESSAGE_FORMAT=  "Telefónné číslo musí mať správny tvar";

    public static String DATE_OF_BIRTH_MESSAGE =  "Dátum narodenia musí byť v minulosti";

    public static String PERSONAL_NUMBER_MESSAGE_NOT_BLANK =  "Rodné číslo je povinné pole";
    public static String PERSONAL_NUMBER_MESSAGE_FORMAT =  "Rodné číslo musí mať správny format";

    public static String IDENTITY_CARD_NUMBER_MESSAGE_NOT_NULL =  "Číslo občianského preukazu je povinné pole";
    public static String IDENTITY_CARD_NUMBER_MESSAGE_FORMAT =  "Číslo občianskeho preukazu musí mať správny tvar";

    public static String CITIZENSHIP_MESSAGE_NOT_NULL =  "Štátne občianstvo je povinné pole";
    public static String CITIZENSHIP_MESSAGE_FORMAT =  "Štátne občianstvo musí mať 2 alebo 3 veľké alfabetické znaky";

    public static String RELEASE_DATE_OF_IDENTITY_CARD_MESSAGE_PAST =  "Dátum vydania OP musí byť v minulosti";
    public static String RELEASE_DATE_OF_IDENTITY_CARD_MESSAGE_BEFORE_VALIDITY_DATE = "Dátum vydania OP musí byť pred dátumom platnosti";

    public static String DATE_OF_VALIDITY_OF_IDENTITY_CARD_MESSAGE_IS_BEFORE_RELEASE_DATE = "Dátum platnosti OP musí byť po dátume vydania";
    public static String DATE_OF_VALIDITY_OF_IDENTITY_CARD_MESSAGE_FUTURE = "Dátum platnosti OP musí byť v buducnosti";



    public static String COPY_CARD_MESSAGE =  "Nebola zadana jedná strana občianského preukazu";

    public static String IBAN_MESSAGE_NOT_EMPTY = "Iban je povinné pole";
    public static String IBAN_MESSAGE_FORMAT = "Iban musí mať správny tvar";

}

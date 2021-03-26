package sk.stu.fei.uim.bp.application.backend.client.web.table;

import org.apache.commons.lang3.StringUtils;

public class TableClientFilter
{

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String personalNumber;
    private String ico;

    public TableClientFilter()
    {
        setName("");
        setSurname("");
        setEmail("");
        setPhone("");
        setPersonalNumber("");
        setIco("");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public boolean test(TableClientItem item)
    {
        if(this.name.length() > 0 && !StringUtils.containsIgnoreCase(item.getName(),this.name))
        {
            return false;
        }

        if(this.surname.length() > 0 && !StringUtils.containsIgnoreCase(item.getSurname(),this.surname))
        {
            return false;
        }

        if(this.email.length() > 0 && !StringUtils.containsIgnoreCase(item.getEmail(),this.email))
        {
            return false;
        }

        if(this.phone.length() > 0 && !StringUtils.containsIgnoreCase(item.getPhone(),this.phone))
        {
            return false;
        }

        if(this.personalNumber.length() > 0 && !StringUtils.containsIgnoreCase(item.getPersonalNumber(),this.personalNumber))
        {
            return false;
        }

        if(this.ico.length() > 0 && !StringUtils.containsIgnoreCase(item.getIco(),this.ico))
        {
            return false;
        }

        return true;
    }
}

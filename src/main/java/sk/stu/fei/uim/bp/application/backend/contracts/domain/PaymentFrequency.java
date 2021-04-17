package sk.stu.fei.uim.bp.application.backend.contracts.domain;

public enum PaymentFrequency
{
    MONTHLY("Mesačne"),
    QUARTERLY("Štvrťročne"),
    HALFYEAR("Polročne"),
    YEARLY("Ročne");

    private String name;

    PaymentFrequency(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}

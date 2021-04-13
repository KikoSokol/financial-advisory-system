package sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain;

public enum ProductTypeCategory
{
    LIFE_INSURANCE("Životné poistenie"),
    NON_LIFE_INSURANCE("Neživotné poistenie"),
    NON_LIFE_CAR_INSURANCE("Neživotné poistenie vozidla");

    private final String name;

    ProductTypeCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

package sk.stu.fei.uim.bp.application.companyAndProductTest;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.ProductTypeService;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTypeTest
{
    @Autowired
    ProductTypeService service;

    @Test
    public void typeTest()
    {
        ProductType toInsert1 = getType1();
        ProductType inserted1 = this.service.addNewProductType(toInsert1);

        assert inserted1 != null;

        System.out.println("toInsert1");
        System.out.println(toInsert1);
        System.out.println("inserted1");
        System.out.println(inserted1);
        toInsert1.setProductTypeId(inserted1.getProductTypeId());
        Assert.assertEquals(toInsert1,inserted1);
        System.out.println("------------------");
        System.out.println();


        ////////////////////////
        ProductType toInsert2 = getType2();
        ProductType inserted2 = this.service.addNewProductType(toInsert2);

        assert inserted2 != null;

        System.out.println("toInsert2");
        System.out.println(toInsert2);
        System.out.println("inserted2");
        System.out.println(inserted2);
        toInsert2.setProductTypeId(inserted2.getProductTypeId());
        Assert.assertEquals(toInsert2,inserted2);
        System.out.println("------------------");
        System.out.println();


        ////////////////////////
        ProductType toInsert3 = getType3();
        ProductType inserted3 = this.service.addNewProductType(toInsert3);

        assert inserted3 != null;

        System.out.println("toInsert3");
        System.out.println(toInsert3);
        System.out.println("inserted3");
        System.out.println(inserted3);
        toInsert3.setProductTypeId(inserted3.getProductTypeId());
        Assert.assertEquals(toInsert3,inserted3);
        System.out.println("------------------");
        System.out.println();


        ////////////////////////
        ProductType toInsert4 = getType3();
        ProductType inserted4 = this.service.addNewProductType(toInsert4);

        assert inserted4 != null;

        System.out.println("toInsert4");
        System.out.println(toInsert4);
        System.out.println("inserted4");
        System.out.println(inserted4);
        toInsert4.setProductTypeId(inserted4.getProductTypeId());
        Assert.assertEquals(toInsert4,inserted4);
        System.out.println("------------------");
        System.out.println();


        ////////////////////////
        ProductType toInsert5 = getType5();
        ProductType inserted5 = this.service.addNewProductType(toInsert5);

        assert inserted5 != null;

        System.out.println("toInsert5");
        System.out.println(toInsert5);
        System.out.println("inserted5");
        System.out.println(inserted5);
        toInsert5.setProductTypeId(inserted5.getProductTypeId());
        Assert.assertEquals(toInsert5,inserted5);
        System.out.println("------------------");
        System.out.println();


        ////////////////////////
        ProductType toInsert6 = getType6();
        ProductType inserted6 = this.service.addNewProductType(toInsert6);

        assert inserted6 != null;

        System.out.println("toInsert6");
        System.out.println(toInsert6);
        System.out.println("inserted6");
        System.out.println(inserted6);
        toInsert6.setProductTypeId(inserted6.getProductTypeId());
        Assert.assertEquals(toInsert6,inserted6);
        System.out.println("------------------");
        System.out.println();


        Optional<ProductType> typeByInserted1ById = this.service.getProductTypeById(inserted1.getProductTypeId());

        assert typeByInserted1ById.isPresent();

        Assert.assertEquals(typeByInserted1ById.get(),inserted1);

        System.out.println("typeByInserted1ById");
        System.out.println(typeByInserted1ById.get());
        System.out.println("------------------");
        System.out.println();


        List<ProductType> byCategoryLife = this.service.getProductTypeByCategory(new ObjectId("601b6300dbf3207494372a20"),ProductTypeCategory.LIFE_INSURANCE);

        Assert.assertEquals(byCategoryLife.get(0),inserted1);
        Assert.assertEquals(byCategoryLife.get(1),inserted2);

        System.out.println("byCategoryLife");
        System.out.println(byCategoryLife);
        System.out.println("------------------");
        System.out.println();


        List<ProductType> byCurrentAgent = this.service.getAllProductTypeOfCurrentAgent(new ObjectId("601b6300dbf3207494372a20"));

        assert  byCurrentAgent.size() == 6;

        System.out.println("byCurrentAgent");
        System.out.println(byCurrentAgent);
        System.out.println("------------------");
        System.out.println();

        List<ProductType> search = this.service.searchProductTypeByName(new ObjectId("601b6300dbf3207494372a20"),"nezivotne");

        assert search.size() == 4;

        System.out.println("search");
        System.out.println(search);
        System.out.println("------------------");
        System.out.println();


        this.service.deleteProductType(inserted1);

        Optional<ProductType> deleted = this.service.getProductTypeById(inserted1.getProductTypeId());

        assert deleted.isEmpty();







    }














    public ProductType getType1()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Zivotne poistenie1");
        productType.setCategory(ProductTypeCategory.LIFE_INSURANCE);

        return productType;
    }

    public ProductType getType2()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Zivotne poistenie2");
        productType.setCategory(ProductTypeCategory.LIFE_INSURANCE);

        return productType;
    }

    public ProductType getType3()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Nezivotne poistenie1");
        productType.setCategory(ProductTypeCategory.NON_LIFE_INSURANCE);

        return productType;
    }

    public ProductType getType4()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Nezivotne poistenie2");
        productType.setCategory(ProductTypeCategory.LIFE_INSURANCE);

        return productType;
    }

    public ProductType getType5()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Nezivotne vozidlo poistenie1");
        productType.setCategory(ProductTypeCategory.NON_LIFE_CAR_INSURANCE);

        return productType;
    }

    public ProductType getType6()
    {
        ProductType productType = new ProductType();
        productType.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        productType.setName("Nezivotne vozidlo poistenie2");
        productType.setCategory(ProductTypeCategory.NON_LIFE_CAR_INSURANCE);

        return productType;
    }


}

package sk.stu.fei.uim.bp.application.companyAndProductTest;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.Product;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductType;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain.ProductTypeCategory;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.CompanyService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductService;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.service.ProductTypeService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest
{

    @Autowired
    ProductTypeService service;
    @Autowired
    ProductService productService;
    @Autowired
    CompanyService companyService;

    @Test
    public void productTest()
    {
        ProductType toInsert1 = getType1();
        ProductType inserted1 = this.service.addNewProductType(toInsert1);

        ProductType toInsert2 = getType2();
        ProductType inserted2 = this.service.addNewProductType(toInsert2);

        ProductType toInsert3 = getType3();
        ProductType inserted3 = this.service.addNewProductType(toInsert3);

        ProductType toInsert4 = getType3();
        ProductType inserted4 = this.service.addNewProductType(toInsert4);

        ProductType toInsert5 = getType5();
        ProductType inserted5 = this.service.addNewProductType(toInsert5);

        ProductType toInsert6 = getType6();
        ProductType inserted6 = this.service.addNewProductType(toInsert6);

        Company companyToInsert1 = getCompany1();
        Company companyInserted1 = this.companyService.addNewCompany(companyToInsert1);

        Company companyToInsert2 = getCompany2();
        Company companyInserted2 = this.companyService.addNewCompany(companyToInsert2);

        ///////////
        //////////


        /////////////////////
        Product productToInsert1 = getProduct1(inserted1.getProductTypeId(),companyInserted1.getCompanyId());
        Product productInserted1 = this.productService.addNewProduct(productToInsert1);

        assert productInserted1 != null;

        System.out.println("productToInsert1");
        System.out.println(productToInsert1);
        System.out.println("productInserted1");
        System.out.println(productInserted1);
        productToInsert1.setProductId(productInserted1.getProductId());
        Assert.assertEquals(productToInsert1,productInserted1);
        System.out.println("------------------");
        System.out.println();


        /////////////////////
        Product productToInsert2 = getProduct2(inserted2.getProductTypeId(),companyInserted1.getCompanyId());
        Product productInserted2 = this.productService.addNewProduct(productToInsert2);

        assert productInserted2 != null;

        System.out.println("productToInsert2");
        System.out.println(productToInsert2);
        System.out.println("productInserted2");
        System.out.println(productInserted2);
        productToInsert2.setProductId(productInserted2.getProductId());
        Assert.assertEquals(productToInsert2,productInserted2);
        System.out.println("------------------");
        System.out.println();

        /////////////////////
        Product productToInsert3 = getProduct3(inserted3.getProductTypeId(),companyInserted1.getCompanyId());
        Product productInserted3 = this.productService.addNewProduct(productToInsert3);

        assert productInserted3 != null;

        System.out.println("productToInsert3");
        System.out.println(productToInsert3);
        System.out.println("productInserted3");
        System.out.println(productInserted3);
        productToInsert3.setProductId(productInserted3.getProductId());
        Assert.assertEquals(productToInsert3,productInserted3);
        System.out.println("------------------");
        System.out.println();


        /////////////////////
        Product productToInsert4 = getProduct4(inserted4.getProductTypeId(),companyInserted2.getCompanyId());
        Product productInserted4 = this.productService.addNewProduct(productToInsert4);

        assert productInserted4 != null;

        System.out.println("productToInsert4");
        System.out.println(productToInsert4);
        System.out.println("productInserted4");
        System.out.println(productInserted3);
        productToInsert4.setProductId(productInserted4.getProductId());
        Assert.assertEquals(productToInsert4,productInserted4);
        System.out.println("------------------");
        System.out.println();


        /////////////////////
        Product productToInsert5 = getProduct5(inserted5.getProductTypeId(),companyInserted2.getCompanyId());
        Product productInserted5 = this.productService.addNewProduct(productToInsert5);

        assert productInserted5 != null;

        System.out.println("productToInsert5");
        System.out.println(productToInsert5);
        System.out.println("productInserted5");
        System.out.println(productInserted5);
        productToInsert5.setProductId(productInserted5.getProductId());
        Assert.assertEquals(productToInsert5,productInserted5);
        System.out.println("------------------");
        System.out.println();


        /////////////////////
        Product productToInsert6 = getProduct6(inserted6.getProductTypeId(),companyInserted2.getCompanyId());
        Product productInserted6 = this.productService.addNewProduct(productToInsert6);

        assert productInserted6 != null;

        System.out.println("productToInsert6");
        System.out.println(productToInsert6);
        System.out.println("productInserted6");
        System.out.println(productInserted6);
        productToInsert6.setProductId(productInserted6.getProductId());
        Assert.assertEquals(productToInsert6,productInserted6);
        System.out.println("------------------");
        System.out.println();

        Optional<Product> byInserted1ById = this.productService.getProductById(productInserted1.getProductId());

        assert  byInserted1ById.isPresent();
        Assert.assertEquals(byInserted1ById.get(),productInserted1);


        List<Product> byCurrentAgent = this.productService.getAllProductOfCurrentAgent(new ObjectId("601b6300dbf3207494372a20"));
        assert byCurrentAgent.size() == 6;


        List<Product> byType = this.productService.getProductByType(inserted1.getProductTypeId());
        assert byType.size() == 1;


        List<Product> byCategory = this.productService.getAllProductByProductTypeCategory(new ObjectId("601b6300dbf3207494372a20"),ProductTypeCategory.LIFE_INSURANCE);
        assert byCategory.size() == 2;

        System.out.println("byCategory");
        System.out.println(byCategory);
        System.out.println("------------------");
        System.out.println();



        Optional<Company> company1 = this.companyService.getCompanyById(companyInserted1.getCompanyId());
        assert company1.isPresent();

        Company cmp1 = company1.get();


        List<Product> ofCompany = this.productService.getAllProductById(cmp1.getProducts());
        assert ofCompany.size() == 3;
        System.out.println(ofCompany.size());

        System.out.println("ofCompany");
        System.out.println(ofCompany);


        Optional<Company> company2 = this.companyService.getCompanyById(companyInserted2.getCompanyId());
        assert company2.isPresent();

        Company cmp2 = company2.get();


        List<Product> ofCompany2 = this.productService.getAllProductById(cmp2.getProducts());
        assert ofCompany.size() == 3;
        System.out.println(ofCompany.size());

        System.out.println("ofCompany2");
        System.out.println(ofCompany2);
        System.out.println("------------------");
        System.out.println();



        List<ObjectId> types = new LinkedList<>();
        types.add(inserted1.getProductTypeId());
        types.add(inserted2.getProductTypeId());

        List<Product> byTypes = this.productService.getAllProductByTypes(types);
        assert byTypes.size() == 2;

        System.out.println("byTypes");
        System.out.println(byTypes);

        List<Product> search = this.productService.searchProductByName(new ObjectId("601b6300dbf3207494372a20"),"CAR");

        assert search.size() == 2;











    }


    public Product getProduct1(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 1 life");
        product.setCompany(company);

        return product;
    }

    public Product getProduct2(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 2 life");
        product.setCompany(company);

        return product;
    }

    public Product getProduct3(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 3 NONLIFE");
        product.setCompany(company);

        return product;
    }

    public Product getProduct4(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 4 NONLIFE");
        product.setCompany(company);

        return product;
    }

    public Product getProduct5(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 5 NONLIFE CAR");
        product.setCompany(company);

        return product;
    }

    public Product getProduct6(ObjectId type,ObjectId company)
    {
        Product product = new Product();
        product.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        product.setProductType(type);
        product.setName("Product 6 NONLIFE CAR");
        product.setCompany(company);

        return product;
    }






    public Company getCompany1()
    {
        Company company = new Company();
        company.setAddress(getAddress1());
        company.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        company.setIco("12345678");
        company.setName("AXA");

        return company;
    }

    public Company getCompany2()
    {
        Company company = new Company();
        company.setAddress(getAddress2());
        company.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        company.setIco("87654321");
        company.setName("GENERALI");

        return company;
    }




    public Address getAddress1()
    {
        Address address = new Address();
        address.setStreet("bajkalska");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Presov");
        address.setState("Slovakia");

        return address;
    }

    public Address getAddress2()
    {
        Address address = new Address();
        address.setStreet("Neviem aka");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Bratislava");
        address.setState("Slovakia");

        return address;
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

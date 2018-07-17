package example.service;

import com.sanmo.smak.example.model.Customer;
import com.sanmo.smak.example.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest(){
        customerService = new CustomerService();
    }

    @Before
    public void init(){

    }

    @Test
    public void getCustomerListTest(){
        List<Customer> list = customerService.getCustomerList();
        Assert.assertEquals(2,list.size());
    }

}

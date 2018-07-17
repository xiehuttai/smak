package example.dao;

import com.sanmo.smak.framework.example.dao.CustomerDao;
import com.sanmo.smak.framework.example.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class CustomerDaoTest {

    private CustomerDao customerDao;

    @Before
    public void prepare(){
        customerDao = new CustomerDao();
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customers = customerDao.getCustomerList();
        for (Customer c: customers){
            System.out.println(c);
        }
        Assert.assertEquals(false,customers.isEmpty());
    }

    @Test
    public void getCustomerTest(){
        Customer customer = customerDao.getCustomer( 1);
        System.out.println(customer);
        Assert.assertEquals(1L,customer.getId());
    }


    @Test
    public void updateCustomerTest(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("email","yangdandan");
        map.put("contact","yangdandan");
        map.put("name","yangdandan");
        boolean success = customerDao.updateCustomer(2, map);
        Assert.assertEquals(true,success);
    }

    @Test
    public void deleteCustomerTest(){
        boolean success = customerDao.deleteCustomer(4);
        Assert.assertEquals(true,success);
    }

    @Test
    public void  insertCustomerTest(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","yangdan");
        map.put("contact","yangdan");
        boolean success = customerDao.insertCustomer(map);
        Assert.assertEquals(true,success);
    }

}

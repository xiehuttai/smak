package com.sanmo.smak.framework.example.dao;

import com.sanmo.smak.framework.example.dao.util.CastUtil;
import com.sanmo.smak.framework.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    public List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>();
        customers = CustomerDbHelper.queryEntityList(Customer.class);
        return customers;
    }

    public Customer getCustomer(long id){
        Customer customer = CustomerDbHelper.getEntity(Customer.class, id);
        return customer;
    }

    public boolean insertCustomer(Customer customer){
        insertCustomer(CastUtil.objectToMap(customer));
        return true;
    }

    public boolean insertCustomer( Map<String,Object> fieldMap){
        CustomerDbHelper.insertEntity(Customer.class,fieldMap);
        return true;
    }

    public boolean updateCustomer(long id , Customer customer){
        return updateCustomer(id, CastUtil.objectToMap(customer));
    }

    public boolean updateCustomer(long id , Map<String,Object> fieldMap){
        return CustomerDbHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(long id){
        return CustomerDbHelper.deleteEntity(Customer.class, id);
    }


}

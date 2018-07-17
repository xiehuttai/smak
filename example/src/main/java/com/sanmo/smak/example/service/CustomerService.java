package com.sanmo.smak.example.service;

import com.sanmo.smak.example.dao.CustomerDao;
import com.sanmo.smak.example.model.Customer;

import java.util.List;
import java.util.Map;

public class CustomerService {

    private CustomerDao customerDao= new CustomerDao();

    public List<Customer> getCustomerList(){
        return customerDao.getCustomerList();
    }

    public Customer getCustomer(long id){
        return customerDao.getCustomer(id);
    }

    public boolean createCustomer(Map<String,Object> fieldMap){
        return customerDao.insertCustomer(fieldMap);
    }

    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return customerDao.updateCustomer(id,fieldMap);
    }

    public boolean deleteCustomer(long id){
       return customerDao.deleteCustomer(id);
    }


}

package com.sanmo.smak.example.service;

import com.sanmo.smak.annotation.Service;
import com.sanmo.smak.annotation.transaction.Transaction;
import com.sanmo.smak.example.dao.CustomerDao;
import com.sanmo.smak.example.model.Customer;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private CustomerDao customerDao= new CustomerDao();

    public List<Customer> getCustomerList(){
        return customerDao.getCustomerList();
    }

    public Customer getCustomer(long id){
        return customerDao.getCustomer(id);
    }

    @Transaction
    public boolean createCustomer(Map<String,Object> fieldMap){
        return customerDao.insertCustomer(fieldMap);
    }

    @Transaction
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return customerDao.updateCustomer(id,fieldMap);
    }

    @Transaction
    public boolean deleteCustomer(long id){
       return customerDao.deleteCustomer(id);
    }
}

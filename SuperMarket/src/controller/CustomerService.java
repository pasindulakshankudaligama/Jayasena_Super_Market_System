package controller;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
  public boolean getCustomers(Customer c) throws SQLException, ClassNotFoundException;
  public Customer passCustomerDetails(String id) throws SQLException, ClassNotFoundException;
  public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}

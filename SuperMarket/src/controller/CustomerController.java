package controller;

import db.DbConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService {

    @Override
    public boolean getCustomers(Customer c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getId());
        stm.setObject(2, c.getCustomerTitle());
        stm.setObject(3, c.getName());
        stm.setObject(4, c.getAddress());
        stm.setObject(5, c.getCity());
        stm.setObject(6, c.getProvince());
        stm.setObject(7, c.getPostalCode());
        return stm.executeUpdate() > 0;
    }

    @Override
    public Customer passCustomerDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Customer WHERE id='" + id + "'").executeQuery();
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {

            return null;
        }
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
}

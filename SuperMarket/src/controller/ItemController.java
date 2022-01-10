package controller;

import db.DbConnection;
import model.Item;
import view.tm.ItemTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements GetItems{

    @Override
    public boolean getItems(Item i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,i.getCode());
        stm.setObject(2,i.getDescription());
        stm.setObject(3,i.getPacketSize());
        stm.setObject(4,i.getUnitPrice());
        stm.setObject(5,i.getQtyOnHand());
        stm.setObject(6,i.getDiscount());


        return stm.executeUpdate() > 0;
    }

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public Item getItem(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item WHERE code='" + id + "'").executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6)
            );
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().
                prepareStatement("DELETE FROM Item WHERE code='" + itemCode +"'").executeUpdate() > 0;
    }

    @Override
    public boolean updateItem(ItemTm item) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().
                getConnection().prepareStatement("UPDATE Item SET  description=?, packetSize=?, unitPrice=?, qtyOnHand=?, discount=? WHERE code=?");
        stm.setObject(1,item.getDescription());
        stm.setObject(2,item.getPacketSize());
       stm.setObject(3,item.getUnitPrice());
        stm.setObject(4,item.getQtyOnHand());
        stm.setObject(5,item.getDiscount());
        stm.setObject(6,item.getCode());
        return stm.executeUpdate() > 0;


    }

}






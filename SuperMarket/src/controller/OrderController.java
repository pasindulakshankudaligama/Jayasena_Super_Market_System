package controller;

import db.DbConnection;
import model.Item;
import model.ItemDetails;
import model.Order;
import view.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

  /*  public boolean updateOrder(Item item) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE `Order Detail` SET unitPrice=?, orderQTY=? WHERE code=?");
        stm.setObject(1,item.getUnitPrice());
        stm.setObject(2,item.getQtyOnHand());
        stm.setObject(3,item.getCode());
        return stm.executeUpdate() > 0;

    }*/

    public List<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Order`").executeQuery();
        List<String> ids= new ArrayList<>();
        while(rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Order` ORDER BY orderId DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId+1;

            if(tempId<9){
            return "O-00" + tempId;

            }else if(tempId<99) {
                return "O-0" + tempId;

            }else {
                return "O-"+tempId;
            }

        }else{
            return "O-001";

        }
    }

    public boolean placeOrder(Order order) throws SQLException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO `Order` Values(?,?,?)");
            stm.setObject(1, order.getOrderId());
            stm.setObject(2, order.getOrderDate());
            stm.setObject(3, order.getCustomerId());


            if (stm.executeUpdate() > 0) {

               if( saveOrderDetails(order.getOrderId(), order.getItems())){
                        con.commit();
                        return true;
           }else{
                con.rollback();
                return false;
            }

                } else {
                     con.rollback();
                    return false;
                }

        } catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();

        }finally {
            {
                con.setAutoCommit(true);
            }
        }
            return false;
        }

    private boolean saveOrderDetails(String orderId, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
        for (ItemDetails temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO `Order Detail` VALUES(?,?,?,?)");
            stm.setObject(1, temp.getItemCode());
            stm.setObject(2, orderId);
            stm.setObject(3, temp.getQtyOnSell());
            stm.setObject(4, temp.getUnitPrice());

            if (stm.executeUpdate() > 0) {
               if (updateQty(temp.getItemCode(), temp.getQtyOnSell())){


                }else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }
    private boolean updateQty(String itemCode,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Item SET qtyOnHand=(qtyOnHand-" + qty + ") WHERE code='" + itemCode + "'");

        return stm.executeUpdate() > 0;
    }

}

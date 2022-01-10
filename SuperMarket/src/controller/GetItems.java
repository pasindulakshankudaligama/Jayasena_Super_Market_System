package controller;

import model.Item;
import view.tm.ItemTm;

import java.sql.SQLException;
import java.util.List;

public interface GetItems {
    public boolean getItems(Item i) throws SQLException, ClassNotFoundException;
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException;
    public Item getItem(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException;
    public boolean updateItem(ItemTm item) throws SQLException, ClassNotFoundException;
}

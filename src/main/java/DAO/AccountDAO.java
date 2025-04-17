package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accountList= new ArrayList<>();
        try {
            String sqlSelect ="SELECT * FROM Account";
            PreparedStatement pStatement= connection.prepareStatement(sqlSelect);
            ResultSet rSet= pStatement.executeQuery();
            while(rSet.next()){
                Account account = new Account(rSet.getInt("account_id"),rSet.getString("username"),rSet.getString("password"));
                accountList.add(account);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
         return accountList;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            if(account.username.length() != 0 && account.password.length()>=4){
            String sqlSelect ="INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement pStatement= connection.prepareStatement(sqlSelect,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, account.getUsername());
            pStatement.setString(2, account.getPassword());

             pStatement.executeUpdate();
             ResultSet pkeyResultSet = pStatement.getGeneratedKeys();
             if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(),account.getPassword());
            }
        }
        else{
            System.out.println("missing value to add to table");
            return null;
        }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

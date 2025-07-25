package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // public List<Account> getAllAccounts(){
    //     Connection connection = ConnectionUtil.getConnection();
    //     List<Account> accountList= new ArrayList<>();
    //     try {
    //         String sqlSelect ="SELECT * FROM Account";
    //         PreparedStatement pStatement= connection.prepareStatement(sqlSelect);
    //         ResultSet rSet= pStatement.executeQuery();
    //         while(rSet.next()){
    //             Account account = new Account(rSet.getInt("account_id"),rSet.getString("username"),rSet.getString("password"));
    //             accountList.add(account);
    //         }
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());

    //     }
    //      return accountList;
    // }

    public Account getAccount(String username,String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sqlSelect = "SELECT * FROM Account WHERE username = ? AND password =?";
            PreparedStatement pStatement= connection.prepareStatement(sqlSelect);
            pStatement.setString(1,username);
            pStatement.setString(2,password);
            ResultSet rSet= pStatement.executeQuery();
            while (rSet.next()) {
                int accountId=rSet.getInt(1);

                if (accountId != 0) {
                    return new Account(accountId,rSet.getString(2),rSet.getString(3));

                } else {
                    return null;
                    
                }
               
            }
            
           return null;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
     
    }

    public Account getAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sqlString=" SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement pStatement= connection.prepareStatement(sqlString);
            pStatement.setInt(1, account_id);
            ResultSet rSet=pStatement.executeQuery();
            if(rSet.next()){
                return new Account(rSet.getInt("account_id"),rSet.getString("username"),rSet.getString("password"));
            }
        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
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

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
}

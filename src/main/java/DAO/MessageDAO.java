package DAO;

import Model.Message;
//import DAO.AccountDAO;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private AccountDAO accountDAO;

    public Message insertMessage(Message message){
        Connection connection= ConnectionUtil.getConnection();
        this.accountDAO= new AccountDAO();
        try {
            String sqlString = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?) ";
            if((accountDAO.getAccount(message.getPosted_by()) != null)){
                PreparedStatement pStatement= connection.prepareStatement(sqlString,Statement.RETURN_GENERATED_KEYS);
                pStatement.setInt(1, message.getPosted_by());
                pStatement.setString(2, message.getMessage_text());
                pStatement.setLong(3, message.getTime_posted_epoch());
                
               pStatement.executeUpdate();
                ResultSet pkeyResultSet=pStatement.getGeneratedKeys();
            
                if(pkeyResultSet.next()){
                    int generated_message_id= (int) pkeyResultSet.getLong(1);
                    return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }
                else{
                    System.out.println("missing value to add to table");
                    return null;
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection= ConnectionUtil.getConnection();
        List<Message> messageList= new ArrayList<>();
        try {
            String sql= "SELECT * FROM Message";
            PreparedStatement pStatement =connection.prepareStatement(sql);
            
            ResultSet rSet= pStatement.executeQuery();
            while (rSet.next()) {
                Message newM = new Message(rSet.getInt(1),rSet.getInt(2),rSet.getString(3),rSet.getLong(4));
                messageList.add(newM);
            }
            return messageList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
      
    }

    public Message getMessage(int messageID){
        Connection connection= ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, messageID);
            ResultSet rSet = pStatement.executeQuery();
            if(rSet.next()){
                return new Message(rSet.getInt(1),rSet.getInt(2),rSet.getString(3),rSet.getLong(4));
            }
            else{
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Message> getAllMessages(int accountID){
        Connection connection= ConnectionUtil.getConnection();
        List<Message> messageList= new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, accountID);
            ResultSet rSet= pStatement.executeQuery();
            while(rSet.next()){
                Message newM = new Message(rSet.getInt(1),rSet.getInt(2),rSet.getString(3),rSet.getLong(4));
                messageList.add(newM);
            }
            return messageList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Message updateMessage(int message_id , String updateMessageText){
        Connection connection= ConnectionUtil.getConnection();
        try {
          
            String sql= "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement pStatement =connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, updateMessageText);
            pStatement.setInt(2, message_id);
          pStatement.executeUpdate();
           
          // ResultSet rSet=pStatement.getGeneratedKeys();
         
           Message updatedMessage = getMessage(message_id);
           if(updatedMessage.getMessage_text().equals(updateMessageText)){
            return updatedMessage;
           }
           else return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    public Message deleteMessage(int message_id){
        Connection connection= ConnectionUtil.getConnection();
        Message removedMessage= getMessage(message_id);
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, message_id);
            pStatement.executeUpdate();
            return removedMessage;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return null;
        }
       
    }
}

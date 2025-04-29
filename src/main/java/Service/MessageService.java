package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message){
        if(message.getMessage_text().length()>0 && message.getMessage_text().length() < 255 ){
            return messageDAO.insertMessage(message);
        }
        else{
            return null;

        }
       
        
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int messageID){
        return messageDAO.getMessage(messageID);
    }

    public List<Message> getAllMessages(int accountID){
        return messageDAO.getAllMessages(accountID);
    }

    public Message updateMessage(int message_id ,String nMessageText){
        if(nMessageText.length()>0 && nMessageText.length()<255 ){
            return messageDAO.updateMessage(message_id, nMessageText);
        }
       else{
        return null;
       }
    }

    public Message deleteMessage(int message_id){
        return messageDAO.deleteMessage(message_id);
    }
}

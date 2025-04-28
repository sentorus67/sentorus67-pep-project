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
        return messageDAO.insertMessage(message);
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
        return messageDAO.updateMessage(message_id, nMessageText);
    }

    public Message deleteMessage(int message_id){
        return messageDAO.deleteMessage(message_id);
    }
}

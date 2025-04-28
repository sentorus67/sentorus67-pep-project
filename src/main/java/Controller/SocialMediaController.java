package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;

import Model.Message;
import Service.MessageService;


import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
     AccountService accountService;
     MessageService messageService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::getAccountHandler);
        app.post("/messages",this::postMessageHandler);
       app.get("/messages", this::getMessagesHandler);
       app.get("/messages/{message_id}", this::getMessageByIdHandler);
       app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
       app.patch("/messages/{message_id}", this::updateMessageHandler);
       app.delete("/messages/{message_id}", this::deleteMessageHandler);


        return app;
    }
  
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    public void postAccountHandler(Context ctx) throws JsonProcessingException{
        this.accountService = new AccountService();
            ObjectMapper mapper = new ObjectMapper();
            Account newAccount = mapper.readValue(ctx.body(), Account.class);

        if( newAccount.username.length() != 0 && newAccount.password.length()>=4){
            Account addedAccount= accountService.addAccount(newAccount);
            try {
            newAccount.setAccount_id(addedAccount.getAccount_id());
            ctx.json(mapper.writeValueAsString(newAccount));
                
            } catch (Exception e) {
                ctx.status(400);
            }
        }
        else{
            ctx.status(400);
        }
            
        }

    public void getAccountHandler(Context ctx) throws JsonProcessingException{
      
        this.accountService = new AccountService();
        ObjectMapper mapper = new ObjectMapper();
        Account Account = mapper.readValue(ctx.body(), Account.class);
        List<Account> prexistingAccounts= accountService.getAllAccounts();
        boolean realAccount=false;
        for (Account account2 : prexistingAccounts) {
  

            if((account2.getUsername().equals(Account.getUsername())) && (account2.getPassword().equals(Account.getPassword()))){

                realAccount=true;
                continue;
            }
        }

        if(realAccount){

            Account returnedAccount = accountService.getAccount(Account.getUsername(), Account.getPassword());
           Account.setAccount_id(returnedAccount.getAccount_id());
            ctx.json(mapper.writeValueAsString(Account));
        }
        else{
            ctx.status(401);
           
        }
    }

    public void postMessageHandler(Context ctx) throws JsonProcessingException{
        this.messageService = new MessageService();
        this.accountService= new AccountService();
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue( ctx.body(), Message.class);
        if(newMessage.getMessage_text().length()>0 && newMessage.getMessage_text().length() < 255 ){
            Message addedMessage = messageService.addMessage(newMessage);
            try {
                newMessage.setMessage_id(addedMessage.getMessage_id());
                ctx.json(mapper.writeValueAsString(newMessage));
                    
                } catch (Exception e) {
                    ctx.status(400);
                }
        }
        else{
            ctx.status(400);
        }
    }

    public void getMessagesHandler (Context ctx) throws JsonProcessingException{
        this.messageService = new MessageService();
        this.accountService= new AccountService();
        List<Message> prexistingMessages= messageService.getAllMessages();
       ctx.json(prexistingMessages);
    }

    public void getMessageByIdHandler (Context ctx) throws JsonProcessingException{
        int mID= Integer.parseInt(ctx.pathParam("message_id"));
        this.messageService = new MessageService();
        Message foundMessage =messageService.getMessage(mID);
        if(foundMessage ==null){
            ctx.json("");
        }
        else{
            ctx.json(foundMessage);
        }
     

    }

    public void getMessagesByAccountHandler(Context ctx) throws JsonProcessingException{
        int accountID= Integer.parseInt(ctx.pathParam("account_id"));
        this.messageService = new MessageService();
        List<Message> foundMessages = messageService.getAllMessages(accountID);
        ctx.json(foundMessages);
    }

    public void updateMessageHandler(Context ctx) throws JsonProcessingException{
        int messageID= Integer.parseInt(ctx.pathParam("message_id"));
       this.messageService =new MessageService();
      try {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue( ctx.body(), Message.class);
        if(newMessage.getMessage_text().length()>0 && newMessage.getMessage_text().length()<255 ){
            Message updatedMessage= messageService.updateMessage( messageID , newMessage.getMessage_text());
            ctx.json(updatedMessage);
        }
        else{
            ctx.status(400);
        }
     
      } catch (Exception e) {
        // TODO: handle exception
        ctx.status(400);
      } 
    }

    public void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        int messageID= Integer.parseInt(ctx.pathParam("message_id"));
        this.messageService =new MessageService();
        Message existingMessage =messageService.getMessage(messageID);
        if( existingMessage == null){
            ctx.status(200);
        }
        else{
            Message deletedMessage= messageService.deleteMessage(messageID);
            ctx.json(deletedMessage);
        }
 
    }
}
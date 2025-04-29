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
            Account addedAccount =accountService.addAccount(newAccount);
            if(addedAccount != null){
                ctx.json(mapper.writeValueAsString(addedAccount));
            }
             else {
            ctx.status(400);
            }
            
        }

    public void getAccountHandler(Context ctx) throws JsonProcessingException{
      
        this.accountService = new AccountService();
        ObjectMapper mapper = new ObjectMapper();
        Account Account = mapper.readValue(ctx.body(), Account.class);
        Account returnedAccount = accountService.getAccount(Account.getUsername(), Account.getPassword());

        if(returnedAccount != null){
            ctx.json(mapper.writeValueAsString(returnedAccount));
        }
        else{
            ctx.status(401);
           
        }
    }

    public void postMessageHandler(Context ctx) throws JsonProcessingException{
        this.messageService = new MessageService();
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue( ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(newMessage);
        if(addedMessage != null ){
            try {
                ctx.json(mapper.writeValueAsString(addedMessage));   
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
        Message updatedMessage= messageService.updateMessage( messageID , newMessage.getMessage_text());
        if(updatedMessage != null ){
            ctx.json(updatedMessage);
        }
        else{
            ctx.status(400);
        }
      } catch (Exception e) {
        ctx.status(400);
      } 
    }

    public void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        int messageID= Integer.parseInt(ctx.pathParam("message_id"));
        this.messageService =new MessageService();
        Message deletedMessage= messageService.deleteMessage(messageID);
        if( deletedMessage == null){
            ctx.status(200);
        }
        else{
            ctx.json(deletedMessage);
        }
 
    }
}
package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;


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
}
import java.util.List;

import Controller.SocialMediaController;
import Model.Account;
import io.javalin.Javalin;
import Service.AccountService;
/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        // AccountService accountService = new AccountService();
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
      
        //  List<Account> prexistingAccounts= accountService.getAllAccounts();
        //  for (Account account : prexistingAccounts) {
        //     System.out.println(account.getUsername());
        //  }

    }
}

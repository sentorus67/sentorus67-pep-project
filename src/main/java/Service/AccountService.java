package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService{
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }


    public Account addAccount(Account account){
        if (account.username.length() !=0 && account.password.length()>=4){
        
            return accountDAO.insertAccount(account);

        }
        else{
            return null;
        }
        
    }

    public Account getAccount(String username,String password){
        return accountDAO.getAccount(username, password);
    }

    public Account getAccount(int accound_id){
        return null;
     }

}
package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService{
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account){
        return accountDAO.insertAccount(account);
      // return null;
    }

    public Account getAccount(String username,String password){
        return accountDAO.getAccount(username, password);
    }


}
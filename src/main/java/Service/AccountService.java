package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService extends Account {
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AuthorService with a new AuthorDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AuthorDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AuthorService independently of AuthorDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
   
    public Account register(Account account) {
        // maybe add: && account.getUsername() == null into the condition?
        if(account.username != "" && account.password.length() >= 4){
            return accountDAO.register(account);
        }
        return null;

        
    }

    public Account login(String username, String password) {
        return accountDAO.login(username, password);
    }
  
    // public Account addAccount(Account account) {
    //     return accountDAO.insertAccount(account);
       
    // }
}

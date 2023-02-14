package Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import javafx.beans.value.WritableValue;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    public AccountService accountService;
    public MessageService messageService;
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */


    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        
       app.post("/register", this::registerHandler);
       app.post("/login", this::loginHandler);
       app.get("/messages", this::getAllMessagesHandler);
       app.post("/messages", this::insertMessageHandler);
       app.patch("/messages/{message_id}", this::updateMessageHandler); 
       app.get("/messages/{message_id}", this::getMessagebyIDHandler);
       app.delete("/messages/{message_id}", this::deleteMessageHandler);
       app.get("/accounts/{account_id}/messages", this::getAllMessagebyAccountIDHandler); 
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    private void registerHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.register(account);
        if(newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
        
    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account existingAccount = accountService.login(account.getUsername(), account.getPassword());
        System.out.println(existingAccount);

        if(existingAccount != null){
            ctx.json(mapper.writeValueAsString(existingAccount));
            ctx.status(200);
        } else{
            ctx.status(401);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }


    private void insertMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        } else{
            ctx.status(400);
        }

    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int Message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message existingMessage = messageService.updateMessagebyID(Message_id, message);

        if(existingMessage != null){
            ctx.json(mapper.writeValueAsString(existingMessage));
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }

    private void getMessagebyIDHandler(Context ctx) throws JsonProcessingException{
        int Message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message existingMessage = messageService.getMessagebyID(Message_id);
        if(existingMessage != null){
            ctx.json(existingMessage);
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }


    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.getMessagebyID(messageID);
        mapper.writeValueAsString(deletedMessage);
        if(deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
            ctx.status(200);
        } 
            
        
    }

    // private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
    //     ObjectMapper mapper = new ObjectMapper();
    //     int messageID = Integer.parseInt(ctx.pathParam("message_id"));
    //     Message deletedMessage = messageService.deleteMessagebyID(messageID);
    //     mapper.writeValueAsString(deletedMessage);
    //     if(deletedMessage != null){
    //         ctx.json(mapper.writeValueAsString(deletedMessage));
    //         ctx.status(200);
    //     }
    // }

    private void getAllMessagebyAccountIDHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessagebyAccountID(Integer.parseInt(ctx.pathParam("account_id")));
        ctx.json(messages);
    }
}



    
  
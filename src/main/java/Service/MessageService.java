package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;


public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
 
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    


    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
   
    public Message createMessage(Message message) {
       
        if(message.message_text != "" && message.message_text.length() < 255){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    
     public Message getMessagebyID(int message_id) {
         return messageDAO.getMessagebyID(message_id);
     }
    
     public Message deleteMessagebyID(int message_id) {
        if ( messageDAO.getMessagebyID(message_id) == null){
            return null;
        }
        return messageDAO.deleteMessagebyID(message_id);
    }

    public Message updateMessagebyID(int message_id, Message message) {
        Message targetMessage = this.messageDAO.getMessagebyID(message_id);
        //maybe add: "targetMessage != null &&" into the condition
        if (message.message_text.length() <= 255 && message.message_text != "" ){
            return messageDAO.updateMessagebyID(message_id, message);
        }
        return null;
    }




    public List<Message> getAllMessagebyAccountID(int account_id) {
        
        return messageDAO.getAllMessagebyAccountID(account_id);
    }

  
}

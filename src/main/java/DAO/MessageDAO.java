package DAO;
// import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import Model.Message;
import Util.ConnectionUtil;
public class MessageDAO {

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message getMessagebyID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
           // String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?);" ; 
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);" ; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch()); //Prob won't need this? Should be auto-generated like message_id. Ask Instructor

            preparedStatement.executeUpdate();
            
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessagebyID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,message_id);

            
            preparedStatement.executeUpdate();
            return getMessagebyID(message_id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    public Message updateMessagebyID(int message_id,Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,message_id);

            preparedStatement.executeUpdate();
            return getMessagebyID(message_id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagebyAccountID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }



    /* IN CASE CHANGING getAllMessagebyAccountID to <List> Message CAUSES ERRORS, USE THE BELOW AND CHANGE ACCORDINGLY */
    // public Message getAllMessagebyAccountID(int account_id) {
    //     Connection connection = ConnectionUtil.getConnection();
    //     try {
    //         //Write SQL logic here
    //         String sql = "SELECT * FROM message WHERE posted_by = ?;";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         preparedStatement.setInt(1,account_id);

    //         ResultSet rs = preparedStatement.executeQuery();
    //         while(rs.next()){
    //             Message message = new Message(rs.getInt("message_id"),
    //                     rs.getInt("posted_by"),
    //                     rs.getString("message_text"),
    //                     rs.getLong("time_posted_epoch"));
    //             return message;
    //         }
    //     }catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }




    // public List<Message> getMessageByID(Message message) {

    //     // Connection connection = ConnectionUtil.getConnection();

    //     // try {
    //     //     //Write SQL logic here
    //     //     String sql = "SELECT * FROM message WHERE message_id = ?;";
    //     //     PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //     //     //write preparedStatement's setInt method here.
    //     //     preparedStatement.setInt(1, message_id);

    //     //     ResultSet rs = preparedStatement.executeQuery();
    //     //     while(rs.next()){
    //     //         Message message = new Message(rs.getInt("message_id"),
    //     //                 rs.getInt("posted_by"),
    //     //                 rs.getString("message_text"),
    //     //                 rs.getLong("time_posted_epoch"));
    //     //         return message;
    //     //     }
    //     // }catch(SQLException e){
    //     //     System.out.println(e.getMessage());
    //     // }

    //     return null;
    // }
    
}

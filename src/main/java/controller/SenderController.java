package controller;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderController {
    private String username,
            host,
            EXCHANGE_NAME;
    private Connection connection;
    private Channel channel;
    private ConnectionFactory factory;

    public SenderController(String username, String host,String EXCHANGE_NAME) {
        this.username = username;
        this.host = host;
        this.EXCHANGE_NAME = EXCHANGE_NAME;
        connect();
    }

    private boolean connect() {
        try
        {
            return createFactory() && newConnection() && createChannel();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(TimeoutException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean close(){
        try {
            channel.close();
            connection.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean createFactory(){
        if(factory == null){factory = new ConnectionFactory();}

        factory.setHost(host);
        factory.setUsername(username);

        return factory.getHost().equals(host);
    }

    private boolean newConnection() throws IOException, TimeoutException {

        if( connection == null )
            connection = factory.newConnection();

        return connection.isOpen();
    }

    private boolean createChannel() throws IOException, TimeoutException {
        if( channel == null )
            channel = connection.createChannel();

        return channel.isOpen();
    }

    public void sendMessage(String msg) {
        String response = "Message could not be sent!";

        response = Publish( msg );

        System.out.println( response );

    }

    private String Publish(String msg){
        try {
            channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.FANOUT);
            channel.queueBind("group3.reciplist.xmltranslator",EXCHANGE_NAME,"");
            channel.queueBind("group3.reciplist.jsontranslator",EXCHANGE_NAME,"");
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[Sent] --> '" + msg + "'";
    }
}

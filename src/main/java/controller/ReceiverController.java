package controller;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ReceiverController {

    private String username, host, QUEUE_NAME;
    private Connection connection;
    private Channel channel;
    private ConnectionFactory factory;

    public ReceiverController(String username, String host, String QUEUE_NAME) {
        this.username = username;
        this.host = host;
        this.QUEUE_NAME = QUEUE_NAME;
        connect();
    }

    private boolean connect() {
        try {
            return createFactory() && newConnection() && createChannel();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( TimeoutException e ) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean close() {
        try {
            channel.close();
            connection.close();
            return true;
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( TimeoutException e ){
            e.printStackTrace();
        }
        return false;
    }

    private boolean createFactory() {
        if( factory == null )
            factory = new ConnectionFactory();

        // datdb.cphbusiness.dk or localhost
        factory.setHost( host );

        // student or guest
        factory.setUsername( username );

        // cph or guest
        // factory.setPassword( "cph" );

        // 5672 if local else 15672
        // factory.setPort( 15672 );

        return factory.getHost().equals( host );
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

    private ArrayList<String> handleDelivery() throws IOException {

        final ArrayList<String> bankMessage = new ArrayList<String>();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        System.out.println("====================================================");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                bankMessage.add(message);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        return bankMessage;
    }

    public boolean isReady(){
        return !getMessages().isEmpty();
    }

    public ArrayList<String> getMessages(){
        try {
            return handleDelivery();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printMessages() {
        try {
            handleDelivery();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
    }

}

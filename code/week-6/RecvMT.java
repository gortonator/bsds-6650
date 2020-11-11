/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmqtests;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecvMT {

    private final static String QUEUE_NAME = "threadExQ";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {                 
                try {
                    final Channel channel = connection.createChannel();
                    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                    // max one message per receiver
                    channel.basicQos(1);
                    System.out.println(" [*] Thread waiting for messages. To exit press CTRL+C");

                    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), "UTF-8");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        System.out.println( "Callback thread ID = " + Thread.currentThread().getId() + " Received '" + message + "'");
                    };
                    // process messages
                    channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
                    } catch (IOException ex) {
                        Logger.getLogger(RecvMT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        // start threads and block to receive messages
        Thread recv1 = new Thread(runnable);
        Thread recv2 = new Thread(runnable);
        recv1.start();
        recv2.start();
    }
}

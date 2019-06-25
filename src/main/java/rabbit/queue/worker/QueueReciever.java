package rabbit.queue.worker;

import java.io.FileReader;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabbit.listener.MessageListener;

public class QueueReciever {

	public static void main(String[] argv) throws Exception {
		Properties props = readProperties();
		registraMessageListner(props.getProperty("HOST"), props.getProperty("QUEUE_NAME"));
	}

	static void registraMessageListner(String host, String queueName) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();


		channel.queueDeclare(queueName, false, false, false, null);
		System.out.println("Receiver esperando mensagens ...");
		
		channel.basicQos(1);
		DeliverCallback callback = new MessageListener();
        channel.basicConsume(queueName, true, callback, consumerTag -> { });
	}
	
	private static Properties readProperties() throws Exception {
		Properties props = new Properties();
		props.load(new FileReader("rabbitmq.properties"));
		return props;
	}

}

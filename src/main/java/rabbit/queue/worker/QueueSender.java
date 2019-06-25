package rabbit.queue.worker;

import java.io.FileReader;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class QueueSender {

	public static void main(String[] argv) throws Exception {
		Properties props = readProperties();
		enviaMensagens(props.getProperty("HOST"), props.getProperty("QUEUE_NAME"));
	}	

	static void enviaMensagens(String host, String queueName) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(queueName, false, false, false, null);
		
		for (int i = 1; i <= 50; i++) {
			String message = "Trabalando com workerqueue, mensagem " + i;
			System.out.println("Enviando mensagem " + i);
			channel.basicPublish("", queueName, null, message.getBytes());			
		}
		channel.close();
		connection.close();
	}
	
	private static Properties readProperties() throws Exception {
		Properties props = new Properties();
		props.load(new FileReader("rabbitmq.properties"));
		return props;
	}

}

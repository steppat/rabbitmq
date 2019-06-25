package rabbit.exchange.direct;

import java.io.FileReader;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExchangeDiretPublisher {

	public static void main(String[] argv) throws Exception {
		Properties props = readProperties();
		enviaMensagens(props.getProperty("HOST"), props.getProperty("EXCHANGE_NAME_DIRECT"));
	}	

	static void enviaMensagens(String host, String exchangeName) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
        channel.exchangeDeclare(exchangeName, "direct");

        String routingKey = "WARN"; //"WARN"
		System.out.println("Enviando para exchange " + exchangeName + ", routingkey: " + routingKey);

		for (int i = 1; i <= 50; i++) {
			String message = "exchange (direct), mensagem " + i;
			System.out.println("Enviando mensagem " + i);
			
			channel.basicPublish(exchangeName, routingKey, null, message.getBytes());	//queue fica sem nome, enviando para exchange		
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

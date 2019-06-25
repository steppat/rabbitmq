package rabbit.exchange.fanout;

import java.io.FileReader;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabbit.listener.MessageListener;

public class ExchangeFanoutSubscriber {

	public static void main(String[] argv) throws Exception {
		Properties props = readProperties();
		registraMessageListner(props.getProperty("HOST"), props.getProperty("EXCHANGE_NAME_FANOUT"));
	}

	static void registraMessageListner(String host, String exchangeName) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		//exchange do tipo fanout (broadcast)
		channel.exchangeDeclare(exchangeName, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		
		//associando a queue criada com o exchange
	    channel.queueBind(queueName, exchangeName, "");
		System.out.println("Receiver esperando mensagens ...");
		
		DeliverCallback callback = new MessageListener();
        channel.basicConsume(queueName, true, callback, consumerTag -> { });
	}
	
	private static Properties readProperties() throws Exception {
		Properties props = new Properties();
		props.load(new FileReader("rabbitmq.properties"));
		return props;
	}

}

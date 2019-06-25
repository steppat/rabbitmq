package rabbit.listener;

import java.io.IOException;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class MessageListener implements DeliverCallback {

	@Override
	public void handle(String consumerTag, Delivery delivery) throws IOException {
		
		String mensagem = new String(delivery.getBody(), "UTF-8");
        System.out.println("Msg: '" + mensagem + "'" + " " + delivery.getEnvelope().getRoutingKey());
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

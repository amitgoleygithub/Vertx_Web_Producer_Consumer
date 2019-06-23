package amit.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class ConsumerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {

		final EventBus eventBus = vertx.eventBus();

		   eventBus.consumer("send_to_consumer_queue", receivedMessage -> {
			   System.out.println("Consumer has Received message: " + receivedMessage.body());		
				   
		       receivedMessage.reply("Finally Consumer has received message successfully");
		       
		   });
	}
}

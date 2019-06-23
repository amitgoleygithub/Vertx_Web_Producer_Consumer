package amit.vertx.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class ProducerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {

		final EventBus eventBus = vertx.eventBus();
		
		   eventBus.consumer("send_to_producer_queue", receivedMessage -> {
			   System.out.println("producer has Received message: " + receivedMessage.body());		
			   System.out.println("Producer will now modify data and send to consumer!");
			      
			   JsonObject localObj = (JsonObject) receivedMessage.body();
			   localObj.put("producer_added", "this is producer");
			   
			   eventBus.send("send_to_consumer_queue", localObj, reply -> {
				      if (reply.succeeded()) {
				    	  System.out.println("producer has received the reply from consumer: " + reply.result().body());
				      } else {
				    	  System.out.println("producer did not get any reply from consumer");
				      }
				   });
			   
		       receivedMessage.reply("producer has received message successfully");
		       
		   });
		  
		   
		   
		   
		   
	}
}

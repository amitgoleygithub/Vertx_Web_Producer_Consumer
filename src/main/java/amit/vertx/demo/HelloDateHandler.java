package amit.vertx.demo;

import java.util.Date;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class HelloDateHandler implements Handler<RoutingContext>{

	@Override
	public void handle(RoutingContext routingContext) {
	
		
		HttpServerResponse response = routingContext.response();
		String dateParam = routingContext.request().getParam("date");
		JsonObject obj = new JsonObject();
		obj.put("helloKeyHandler", "helloValueHandler");
		obj.put("dateParam", dateParam);
		obj.put("date_time", new Date().toString());
		
		System.out.println("received request with thread name = "+Thread.currentThread().getName());
/*		try {
			System.out.println("waiting in hello date handler with thread name = "+Thread.currentThread().getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		EventBus eventBus = routingContext.vertx().eventBus();
		eventBus.send("send_to_producer_queue", obj, reply -> {
		      if (reply.succeeded()) {
		          System.out.println("HelloDateHandler Received reply from producer: " + reply.result().body());
		      } else {
		    	  System.out.println("HelloDateHandler did not get any reply from producer");
		      }
		   });
		
		routingContext.response()
								.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(Json.encodePrettily(obj));
		
		System.out.println("completed in hello date handler with thread name = "+Thread.currentThread().getName());
		
		
	}
	
	

}

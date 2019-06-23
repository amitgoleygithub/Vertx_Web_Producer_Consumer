package amit.vertx.demo;

import java.util.Random;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

//http://localhost:8080/hello/2019

public class DemoServerVerticle extends AbstractVerticle{

	Random rand = new Random(); 
	
	@Override
	public void start() throws Exception {

		try {
			
			HttpServer httpServer = vertx.createHttpServer();
			//Main router
			Router router = Router.router(vertx);		
			router.route().handler(BodyHandler.create());			
			router.get("/hello/:date").blockingHandler(new HelloDateHandler()); // handler as a seperate class
			//router.get("/").handler(this::sayHello);             // handler in same class              

			
			httpServer.requestHandler(router::accept).listen(8080);
			
			System.out.println("Server Started");			   
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void sayHello(RoutingContext routingContext) {
		
		HttpServerResponse response = routingContext.response();
		
		int randomnumber = rand.nextInt();
		System.out.println("received request="+randomnumber);
		System.out.println("processing request = "+randomnumber);
		JsonObject obj = new JsonObject();
		obj.put("helloKeyRoot", "helloValueRoot");
		
		//Blocking the eventloop
		try {
			System.out.println("waiting in hello handler with thread name = "+Thread.currentThread().getName()); //eventloop thread
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		routingContext.response()
								.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(Json.encodePrettily(obj));
		
		System.out.println("completed request ="+randomnumber);
		System.out.println("**************************************************");
	}
	
}

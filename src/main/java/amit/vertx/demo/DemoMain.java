package amit.vertx.demo;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;



public class DemoMain {

	public static void main( String[] args )
    {
		System.out.println("initial thread count = "+Thread.activeCount());;
    	Vertx vertx = Vertx.factory.vertx();
    	
    	DeploymentOptions deployOptions = new DeploymentOptions().setInstances(4);
    	deployOptions.setWorkerPoolSize(20);  // can be configured
    	
    	vertx.deployVerticle(DemoServerVerticle.class.getName(),deployOptions,res ->{         //deploying asyn
    		if (res.succeeded()) {
    			System.out.println("Server Verticle Deployment success with id is: " + res.result());
    	      } else {
    	    	  System.out.println("Server Verticle Deployment failed!");
    	      }
    	});
    	
    	vertx.deployVerticle(ProducerVerticle.class.getName(),deployOptions,res ->{         //deploying asyn
    		if (res.succeeded()) {
    			System.out.println("Producer Verticle Deployment success with id is: " + res.result());
    	      } else {
    	    	  System.out.println("Producer Verticle Deployment failed!");
    	      }
    	});
    	
    	vertx.deployVerticle(ConsumerVerticle.class.getName(),deployOptions,res ->{         //deploying asyn
    		if (res.succeeded()) {
    			System.out.println("Consumer Verticle Deployment success with id is: " + res.result());
    	      } else {
    	    	  System.out.println("Consumer Verticle Deployment failed!");
    	      }
    	});
     	
    	System.out.println("after thread count = "+Thread.activeCount());;
    	
    	System.out.println("Available processors="+Runtime.getRuntime().availableProcessors());
    	System.out.println("Server Started and thread count = "+Thread.activeCount());	
    	
      	
    }
}

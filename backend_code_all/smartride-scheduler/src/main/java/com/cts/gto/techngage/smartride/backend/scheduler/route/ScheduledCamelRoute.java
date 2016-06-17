package com.cts.gto.techngage.smartride.backend.scheduler.route;


import java.util.Properties;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;


import com.cts.gto.techngage.smartride.backend.scheduler.job.CommonUtil;
import com.cts.gto.techngage.smartride.backend.scheduler.job.TripScheduleJob;


import org.apache.camel.component.file.GenericFile;

public class ScheduledCamelRoute extends SpringRouteBuilder {
	
	private static Properties schedulerProperties; 
	private static String offsetMinute;

    @Override
    public void configure() throws Exception {
    	
    	schedulerProperties = CommonUtil.readPropertyFileFromClasspath("scheduler.properties");
    	offsetMinute = (String) schedulerProperties.get("offset_min");
    	
    	String cronExp = "0+0/"+ offsetMinute +"+6-18+?+*+*";
    	
    	from("quartz2://smartride_quartz_group/vehicleAllocTimer?cron="+cronExp)
    	.routeId("smartrideCamelRoute1")
    	.process(new Processor() {
                public void process(Exchange exchange) throws Exception {                	
                    String text = (String) exchange.getIn().getBody();
                    System.out.println("smartrideCamelRoute : schedule launched....");              	
                    
                    TripScheduleJob tripScheduleJob = new TripScheduleJob();
                    tripScheduleJob.scheduleUpcomingTrips(offsetMinute);
                    
                    System.out.println("smartrideCamelRoute : schedule completes");
                }
         });
    	
    	if(true) {
    		return;
    	}

        //prop = CommonUtil.readPropertyFile(System.getProperty("sys_prop_uri"));
        String respFileZone = "C:/temp";  //prop.getProperty("response_file_zone");        
        String commandFileZone =  "C:/temp/command"; //prop.getProperty("commandlist_file_zone"); 
        
        from("file:///"+respFileZone)
        .process(new Processor() {
                public void process(Exchange exchange) throws Exception {                	
                    Object text = exchange.getIn().getBody();  
                    String fileName = ((GenericFile)text).getFileNameOnly();
                    exchange.getIn().setHeader("file-name-only", fileName);
                    
                	System.out.println("fileName : "+ fileName);                	
                    System.out.println("Exiting process()....0");
                }
         })
        .convertBodyTo(String.class)
        .process(new Processor() {
                public void process(Exchange exchange) throws Exception {                	
                    String text = (String) exchange.getIn().getBody();
                    
                    String hFilename = (String) exchange.getIn().getHeader("file-name-only");
                	System.out.println("String body ["+hFilename+"] : "+ text); 
                	
                	//text = processExecutionResponse(hFilename, text);                	
                	//exchange.getIn().setBody(text);                	
                    System.out.println("Exiting process()....1");
                }
         });
        
        
        //-- Command Route --------
        from("file:///"+commandFileZone)
        .process(new Processor() {
            public void process(Exchange exchange) throws Exception {                	
                Object text = exchange.getIn().getBody();  
                String fileName = ((GenericFile)text).getFileNameOnly();
                exchange.getIn().setHeader("file-name-only", fileName);
                
            	System.out.println("fileName : "+ fileName); 
            	
                System.out.println("Command process()....0");
            }
	     })
	    .convertBodyTo(String.class)
	    .process(new Processor() {
	            public void process(Exchange exchange) throws Exception {                	
	                String text = (String) exchange.getIn().getBody();
	                
	                String hFilename = (String) exchange.getIn().getHeader("file-name-only");
	            	System.out.println("String body ["+hFilename+"] : "+ text); 
	            	
	            	//text = processInPageRequests(hFilename, text);                	
	            	//exchange.getIn().setBody(text);                	
	                System.out.println("Command process()....1");
	            }
	     });
        
    }
    

   
}

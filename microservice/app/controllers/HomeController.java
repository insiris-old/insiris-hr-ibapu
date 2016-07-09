package controllers;

import models.Job;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import play.mvc.*;
import com.datastax.driver.core.*;
import play.data.Form;


public class HomeController extends Controller {
	
   private Cluster cluster;  
   private Session session;  
   
   public HomeController(){
	   cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
	   session = cluster.connect("insiriskeyspace");
   }

    public Result create() {
    	Form <Job> userForm = Form.form(Job.class).bindFromRequest();
    	Job job = userForm.get();
    	session.execute("INSERT INTO Jobs (job_id, customerName, notes, address) VALUES (" + job.job_id + " , '" + job.customerName + "' , '" + job.notes + "' , '" + job.address + "' )");
        return ok();
    }
    
    public Result read() {
     	ResultSet results = session.execute("select * from Jobs");
     	JSONObject jobObject = new JSONObject();
     	JSONArray jobsArray = new JSONArray();
     	for (Row row : results) {
     		jobObject.put("job_id", row.getInt("job_id"));
     		jobObject.put("customerName", row.getString("customerName"));
     		jobObject.put("notes", row.getString("notes"));
     		jobObject.put("address", row.getString("address"));
     		jobsArray.add(jobObject);
     		jobObject = new JSONObject();
     	}
     	return ok(jobsArray.toJSONString());
    }
    
    public Result update() {
    	Form <Job> userForm = Form.form(Job.class).bindFromRequest();
    	Job job = userForm.get();
    	session.execute("update Jobs set customerName = '" + job.customerName + "',notes = '" + job.notes + "',address = '" + job.address + "' where job_id = " + job.job_id);
        return ok();
    }
    
    public Result delete() {
    	Form <Job> userForm = Form.form(Job.class).bindFromRequest();
    	Job job = userForm.get();
    	session.execute("DELETE FROM Jobs where job_id = " + job.job_id);
        return ok();
    }
    
}



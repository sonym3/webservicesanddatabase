/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasehandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author 1895268
 */
@Path("generic2")
public class GenericResource2 {
    
    
    @Context
    private UriInfo context;

    public GenericResource2() {
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return "hai";
    }
    
    
    public int insertJobs(String jobID, String jobTittle, int minSalary, int maxSalary) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="insert into jobs values (?,?,?,?) ";
            stm=con.prepareStatement(sql);
            stm.setString(1,jobID);
            stm.setString(2, jobTittle);
            stm.setInt(3, minSalary);
            stm.setInt(4, maxSalary);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(GenericResource2.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }
    
    //REGIONS
    @GET
    @Path("insert&{value1}&{value2}&{value3}&{value4}")
    @Produces(MediaType.TEXT_PLAIN)
    public String regionsInsert(
            @PathParam("value1") String jobID,
            @PathParam("value2") String jobTittle,
            @PathParam("value3") int minSalary,
            @PathParam("value4") int maxSalary) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=insertJobs(jobID, jobTittle, minSalary, maxSalary);
            if(result> 0){
            status="Success";
            message=result + " updated Successfully";
            mainobject.accumulate("Status :", status);
            mainobject.accumulate("Timestamp :", time);
            mainobject.accumulate("Message :", message);
            }
            else{
            status="Failed";
            message=result + " updated Successfully";
            mainobject.accumulate("Status :", status);
            mainobject.accumulate("Timestamp :", time);
            mainobject.accumulate("Message :", message);
            }
         return mainobject.toString();
    }
    
     public int deleteJobs(String job_id) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="delete from jobs where job_id=?";
            stm=con.prepareStatement(sql);
            stm.setString(1,job_id);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(GenericResource2.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }

    
    @GET
    @Path("delete&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String jobsDelete(@PathParam("value1") String jobID) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=deleteJobs(jobID);
            if(result> 0){
            status="Success";
            message=result + " deleted Successfully";
            mainobject.accumulate("Status :", status);
            mainobject.accumulate("Timestamp :", time);
            mainobject.accumulate("Message :", message);
            }
            else{
            status="Failed";
            message=result + " deleted Successfully";
            mainobject.accumulate("Status :", status);
            mainobject.accumulate("Timestamp :", time);
            mainobject.accumulate("Message :", message);
            }
         return mainobject.toString();
    }

    public JSONObject singleJobs(String jobID) throws SQLException {
        
        ResultSet result;
        Connection con=null;
        PreparedStatement stm=null;
        JSONObject mainObject1 = new JSONObject();
        
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="Select * from jobs where job_id=?";
            stm=con.prepareStatement(sql);
            stm.setString(1, jobID);
            result=stm.executeQuery();
            String status;
            
            Instant instant = Instant.now();
            long time = instant.getEpochSecond();
            
            if(result.next() == false){
                status="Failed";
                mainObject1.accumulate("Status :", status);
                mainObject1.accumulate("Timestamp :", time);
                mainObject1.accumulate("Message :", " Fetching Failed");
               
                }  
            
            else {
                do{
                  status="Success";   
                    mainObject1.accumulate("Status :", status);
                    mainObject1.accumulate("Timestamp :", time);
                    mainObject1.accumulate("Job ID :", result.getString("job_id"));
                    mainObject1.accumulate("Job Name",result.getString("job_title"));
                    mainObject1.accumulate("min salary",result.getInt("min_salary"));
                    mainObject1.accumulate("max salary",result.getInt("max_salary"));
                  }while(result.next());
                }  
           stm.close();
           con.close();
           
    }
    catch (SQLException ex) {
            Logger.getLogger(GenericResource2.class.getName()).log(Level.SEVERE, null, ex);
        } 
  
        return mainObject1;
    }

    
    @GET
    @Path("single&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String jobsSingleList(@PathParam("value1") String jobsID ) throws SQLException {
        
        
        
        JSONObject result = singleJobs(jobsID);
            
         return result.toString();
          }
    
    
    
    public int updateJob(String rname, int rid){
   
        Connection con=null;
        PreparedStatement stm=null;
       
        int result = 0;
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
             String sql="update jobs set job_title= ?, min_salary=?, max_salary=? where job_id=?";
             stm=con.prepareStatement(sql);
             stm.setString(1, rname);
             stm.setInt(2, rid);
             result=stm.executeUpdate();
             
             stm.close();
             con.close();

           
         } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    return result;
    }
    
    @GET
     @Path("update&{val1}&{val2}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText(@PathParam ("val1") String rname, @PathParam ("val2") int rid ) {
       
       
        Instant instant=Instant.now();
        long time=instant.getEpochSecond();
       
    int result=updateRegion(rname, rid);
   
    JSONObject mainobject=new JSONObject();
   
    if(result>0){
    String status="OK";
    String message= result + " rows updated";
           
    mainobject.accumulate("status", status);
    mainobject.accumulate("Timestamp", time);
    mainobject.accumulate("Message", message);
   
    }
    else{
   
    String status="Failed";
    String message= result + " rows updated";
           
    mainobject.accumulate("status", status);
    mainobject.accumulate("Timestamp", time);
    mainobject.accumulate("Message", message);
   
   
    }
    return mainobject.toString();
    }
    
    
    public JSONObject listJobs(){
   
        Connection con=null;
        Statement stmt=null;
        JSONObject mainObject1=new JSONObject();
        JSONObject jsonObject=new JSONObject();
        String status=null;
       
        
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
            String sql="select * from jobs";
            stmt=con.createStatement();
            ResultSet result=stmt.executeQuery(sql);
            JSONArray jsonArray=new JSONArray();
            Instant instant=Instant.now();
            long time=instant.getEpochSecond();
             
   
           if(result.next() == false){
                status="Faild";
                mainObject1.accumulate("Status :", status);
                mainObject1.accumulate("Timestamp :", time);
                mainObject1.accumulate("Message :", " Fetching Failed");
               
                }  
            
            else {
                mainObject1.accumulate("Status :", "OK");
                mainObject1.accumulate("Timestamp :", time);
                do{
                    jsonObject.accumulate("Job ID: ", result.getString(1));
                    jsonObject.accumulate("Job Name: ", result.getString(2));
                    jsonObject.accumulate("Min Salary: ", result.getInt(3));
                    jsonObject.accumulate("Max Salary: ", result.getInt(4));
                    jsonArray.add(jsonObject);
                    jsonObject.clear();
                }while(result.next());
                mainObject1.accumulate("Details of Jobs: ", jsonArray);
                }  
             
             
             
             stmt.close();
             con.close();

           
         } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mainObject1;
    
    }
    
    @GET
     @Path("jobsfulllist")
    @Produces(MediaType.TEXT_PLAIN)
        public String getListRegions() {   
        JSONObject mainobject=new JSONObject();
        mainobject=listJobs();
        return mainobject.toString();
    }
    
    
}

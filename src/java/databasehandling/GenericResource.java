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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * REST Web Service
 *
 * @author 1895268
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    public GenericResource() {
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return "hai";
    }
    
    
    public int insertRegions(int regionID, String regionName) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="insert into regions values (?,?) ";
            stm=con.prepareStatement(sql);
            stm.setInt(1,regionID);
            stm.setString(2, regionName);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }
    
    //REGIONS
    @GET
    @Path("insert&{value1}&{value2}")
    @Produces(MediaType.TEXT_PLAIN)
    public String regionsInsert(@PathParam("value1") int regionID,
            @PathParam("value2") String regionName) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=insertRegions(regionID, regionName);
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
    
     public int deleteRegions(int regionID) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="delete from regions where region_id=?";
            stm=con.prepareStatement(sql);
            stm.setInt(1,regionID);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }

    
    @GET
    @Path("delete&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String regionsDelete(@PathParam("value1") int regionID) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=deleteRegions(regionID);
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

    public JSONObject singleRegions(int regionID) throws SQLException {
        
        ResultSet result;
        Connection con=null;
        PreparedStatement stm=null;
        JSONObject mainObject1 = new JSONObject();
        
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="Select * from regions where region_id=?";
            stm=con.prepareStatement(sql);
            stm.setInt(1,regionID);
            result=stm.executeQuery();
            String status;
            
            Instant instant = Instant.now();
            long time = instant.getEpochSecond();
            
            if(result.next() == false){
                status="Faild";
                mainObject1.accumulate("Status :", status);
                mainObject1.accumulate("Timestamp :", time);
                mainObject1.accumulate("Message :", " Fetching Failed");
               
                }  
            
            else {
                do{
                  status="Success";   
                    mainObject1.accumulate("Status :", status);
                    mainObject1.accumulate("Timestamp :", time);
                    mainObject1.accumulate("Region ID :", result.getInt("region_id"));
                    mainObject1.accumulate("Region Name",result.getString("region_name"));
                  }while(result.next());
                }  
           stm.close();
           con.close();
           
    }
    catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } 
  
        return mainObject1;
    }

    
    @GET
    @Path("single&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String regionsSingle(@PathParam("value1") int regionID) throws SQLException {
        
        
        
        JSONObject result = singleRegions(regionID);
            
         return result.toString();
          }
    
    
    
    public int updateRegion(String rname, int rid){
   
        Connection con=null;
        PreparedStatement stm=null;
       
        int result = 0;
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
             String sql="update regions set region_name= ? where region_id=?";
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
    
    
    public JSONObject listRegions(){
   
        Connection con=null;
        Statement stmt=null;
        JSONObject mainObject1=new JSONObject();
        JSONObject jsonObject=new JSONObject();
        String status=null;
       
        
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
            String sql="select * from regions";
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
                    jsonObject.accumulate("region ID: ", result.getInt(1));
                    jsonObject.accumulate("Region_Name: ", result.getString(2));
                    jsonArray.add(jsonObject);
                    jsonObject.clear();
                }while(result.next());
                mainObject1.accumulate("Details of regions: ", jsonArray);
                }  
             
             
             
             stmt.close();
             con.close();

           
         } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mainObject1;
    
    }
    
    @GET
     @Path("regionsfulllist")
    @Produces(MediaType.TEXT_PLAIN)
        public String getListRegions() {   
        JSONObject mainobject=new JSONObject();
        mainobject=listRegions();
        return mainobject.toString();
    }
    
    
    
}



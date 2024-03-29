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
@Path("countries")
public class Countries {
    
    
    @Context
    private UriInfo context;

    public Countries() {
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return "hai";
    }
    
    
    public int insertCountries(String countryID, String countryName, int regionID) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="insert into countries values (?,?,?) ";
            stm=con.prepareStatement(sql);
            stm.setString(1,countryID);
            stm.setString(2, countryName);
            stm.setInt(3, regionID);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(Jobs.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }
    
    //REGIONS
    @GET
    @Path("insert&{value1}&{value2}&{value3}")
    @Produces(MediaType.TEXT_PLAIN)
    public String countriesInsert(
            @PathParam("value1") String countryID,
            @PathParam("value2") String countryName,
            @PathParam("value3") int regionID) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=insertCountries(countryID, countryName, regionID);
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
    
     public int deleteCountries(String countryID) {
        
        int result = 0;
        Connection con=null;
        PreparedStatement stm=null;
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="delete from countries where country_id=?";
            stm=con.prepareStatement(sql);
            stm.setString(1,countryID);
            result=stm.executeUpdate();
       
            
           stm.close();
           con.close();
        
          } catch (SQLException ex) {
            Logger.getLogger(Jobs.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
        
    }

    
    @GET
    @Path("delete&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String countriesDelete(@PathParam("value1") String countryID) {
        
        
        String status=null;
        String message = null;
        
        Instant instant = Instant.now();
        long time = instant.getEpochSecond();
        
        
        JSONObject mainobject=new JSONObject();
        
            int result=deleteCountries(countryID);
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

    
    
    public JSONObject singleCountries(String country_id) throws SQLException {
        
        ResultSet result;
        Connection con=null;
        PreparedStatement stm=null;
        JSONObject mainObject1 = new JSONObject();
        
        try{
          
         
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
          String sql;
    
            sql="Select * from countries where country_id=?";
            stm=con.prepareStatement(sql);
            stm.setString(1, country_id);
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
                    mainObject1.accumulate("Country ID :", result.getString("country_id"));
                    mainObject1.accumulate("Country Name",result.getString("country_name"));
                    mainObject1.accumulate("Region ID: ",result.getInt("region_id"));
                }while(result.next());
                }  
           stm.close();
           con.close();
           
    }
    catch (SQLException ex) {
            Logger.getLogger(Jobs.class.getName()).log(Level.SEVERE, null, ex);
        } 
  
        return mainObject1;
    }

    
    @GET
    @Path("single&{value1}")
    @Produces(MediaType.TEXT_PLAIN)
    public String countiresSingleList(@PathParam("value1") String country_id ) throws SQLException {
        
        
        
        JSONObject result = singleCountries(country_id);
            
         return result.toString();
          }
    
     public int updateCountries(String countryID, String countryName, int regionID){
   
        Connection con=null;
        PreparedStatement stm=null;
       
        int result = 0;
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
             String sql="update countires set country_name= ?, region_id=? where country_id=?";
             stm=con.prepareStatement(sql);
             stm.setString(1, countryName);
             stm.setInt(2, regionID);
             stm.setString(3, countryID);
             result=stm.executeUpdate();
             
             stm.close();
             con.close();

           
         } catch (SQLException ex) {
            Logger.getLogger(Jobs.class.getName()).log(Level.SEVERE, null, ex);
        }
    return result;
    }
    
    @GET
     @Path("update&{value1}&{value2}&{value3}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText(@PathParam ("value1") String countryID, 
            @PathParam ("value2") String countryName,
            @PathParam ("value3") int regionID){
       
       
        Instant instant=Instant.now();
        long time=instant.getEpochSecond();
       
    int result=updateCountries(countryID, countryName, regionID);
   
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
    
    
    public JSONObject listCountries(){
   
        Connection con=null;
        Statement stmt=null;
        JSONObject mainObject1=new JSONObject();
        JSONObject jsonObject=new JSONObject();
        String status=null;
       
        
        try{
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con=DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
           
            String sql="select * from countries";
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
                    jsonObject.accumulate("Country ID: ", result.getString(1));
                    jsonObject.accumulate("Country Name: ", result.getString(2));
                    jsonObject.accumulate("Region ID : ", result.getInt(3));
                    jsonArray.add(jsonObject);
                    jsonObject.clear();
                }while(result.next());
                mainObject1.accumulate("Details of Countries: ", jsonArray);
                }  
             
             
             
             stmt.close();
             con.close();

           
         } catch (SQLException ex) {
            Logger.getLogger(Jobs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mainObject1;
    
    }
    
    @GET
     @Path("countireslistfull")
    @Produces(MediaType.TEXT_PLAIN)
        public String getListCountries() {   
        JSONObject mainobject=new JSONObject();
        mainobject=listCountries();
        return mainobject.toString();
    }
    
    
}

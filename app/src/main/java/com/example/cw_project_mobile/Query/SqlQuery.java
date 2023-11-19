package com.example.cw_project_mobile.Query;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.cw_project_mobile.Authenticate.SignIn;
import com.example.cw_project_mobile.DataConnect.DatabaseConnect;
import com.example.cw_project_mobile.Object.Hikes;
import com.example.cw_project_mobile.Object.Observations;
import com.example.cw_project_mobile.Object.Users;

public class SqlQuery {

    public void insertHike(String hikeName, String hikeLocation, String hikeParking,
                           String hikeLength, String hikeLevel, String hikeDescription, String img, int id){

        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        Hikes hikes = new Hikes();
        hikes.setHike_name(hikeName);
        hikes.setHike_location(hikeLocation);
        hikes.setParking(hikeParking);
        hikes.setHike_length(hikeLength);
        hikes.setLevel(hikeLevel);
        hikes.setHike_description(hikeDescription);
        hikes.setHike_image(img);

        try{
            if(conn != null){
                String sql = "insert into hikes values('"+hikes.getHike_name()+"', '"+hikes.getHike_location()+"', " +
                        "'"+currentDate+"', '"+hikes.getParking()+"', '"+hikes.getHike_length()+"', '"+hikes.getLevel()+"', " +
                        "'"+id+"', '"+hikes.getHike_description()+"', '"+hikes.getHike_image()+"', '')";

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }


    public void insertObservation(String obserName, String obserDescription, String obserImage, int hike_id){

        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());

        Observations observations = new Observations();
        observations.setObser_name(obserName);
        observations.setObser_description(obserDescription);
        observations.setObser_img(obserImage);

        try{
            if(conn != null){
                String sql = "insert into observation values('"+observations.getObser_name()+"', '"+currentDateAndTime+"', " +
                        "'"+observations.getObser_description()+"', '"+hike_id+"', '"+observations.getObser_img()+"')";

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void insertUser(String uName, String uEmail, String uPassword, String uAddress){

        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        Users users = new Users();
        users.setUsername(uName);
        users.setEmail(uEmail);
        users.setPassword(uPassword);
        users.setAddress(uAddress);

        try{
            if(conn != null){
                String sql = "insert into users values('"+users.getUsername()+"', '"+users.getPassword()+"', " +
                        "'"+users.getEmail()+"', '"+users.getAddress()+"', 'guest', '')";

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    users.setId(rs.getInt(1));
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public ArrayList<Hikes> selectAllHikes(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Hikes> lstHikes;
        lstHikes = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select * from hikes where share = 'yes'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    Hikes hikes = new Hikes();
                    hikes.setId(rs.getInt(1));
                    hikes.setHike_name(rs.getString(2));
                    hikes.setHike_location(rs.getString(3));
                    hikes.setHike_date(rs.getString(4));
                    hikes.setParking(rs.getString(5));
                    hikes.setHike_length(rs.getString(6));
                    hikes.setLevel(rs.getString(7));
                    hikes.setHike_description(rs.getString(9));
                    hikes.setHike_image(rs.getString(10));
                    lstHikes.add(hikes);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return lstHikes;
    }


    public ArrayList<Hikes> selectMyHikes(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Hikes> lstMyHikes;
        lstMyHikes = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select * from hikes where user_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    Hikes myHikes = new Hikes();
                    myHikes.setId(rs.getInt(1));
                    myHikes.setHike_name(rs.getString(2));
                    myHikes.setHike_location(rs.getString(3));
                    myHikes.setHike_date(rs.getString(4));
                    myHikes.setParking(rs.getString(5));
                    myHikes.setHike_length(rs.getString(6));
                    myHikes.setLevel(rs.getString(7));
                    myHikes.setHike_description(rs.getString(9));
                    myHikes.setHike_image(rs.getString(10));
                    lstMyHikes.add(myHikes);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return lstMyHikes;
    }

    public ArrayList<Observations> selectObservationsOfHike(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Observations> lstObservations;
        lstObservations = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select * from observation where id_of_hike = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    Observations observations = new Observations();
                    observations.setObser_id(rs.getInt(1));
                    observations.setObser_name(rs.getString(2));
                    observations.setDate(rs.getString(3));
                    observations.setObser_description(rs.getString(4));
                    observations.setObser_img(rs.getString(6));
                    lstObservations.add(observations);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return lstObservations;
    }

    public ArrayList<Users> selectAllUsers(String username, String password){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Users> lstUsers;
        lstUsers = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select * from users";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                   if(username.toString().equals(rs.getString(2)) && password.toString().equals(rs.getString(3))){
                       Users users = new Users();
                       users.setId(rs.getInt(1));
                       users.setUsername(rs.getString(2));
                       users.setPassword(rs.getString(3));
                       users.setEmail(rs.getString(4));
                       users.setAddress(rs.getString(5));
                       users.setAvatar(rs.getString(7));
                       lstUsers.add(users);
                       break;
                   }
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return lstUsers;
    }

    public int selectHikeMaxID(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        int maxId = 0;

        try{
            if(conn != null){
                String sql = "select max(hike_id) from hikes";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    maxId = rs.getInt(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return maxId;
    }

    public ArrayList<Users> selectUserInfor(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Users> userInfor = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select username, email from users";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    Users users = new Users();
                    users.setUsername(rs.getString(1));
                    users.setEmail(rs.getString(2));

                    userInfor.add(users);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return userInfor;
    }

    public ArrayList<Users> selectUserInfor(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        ArrayList<Users> userInfor = new ArrayList<>();

        try{
            if(conn != null){
                String sql = "select username, email from users where id != '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    Users users = new Users();
                    users.setUsername(rs.getString(1));
                    users.setEmail(rs.getString(2));

                    userInfor.add(users);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return userInfor;
    }

    public int selectObservationMaxID(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        int maxId = 0;

        try{
            if(conn != null){
                String sql = "select max(observation_id) from observation";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    maxId = rs.getInt(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return maxId;
    }

    public int selectUserMaxID(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        int maxId = 0;

        try{
            if(conn != null){
                String sql = "select max(id) from users";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    maxId = rs.getInt(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return maxId;
    }

    public int selectCountHikes(){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        int countId = 0;

        try{
            if(conn != null){
                String sql = "select count(hike_id) from hikes";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    countId = rs.getInt(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return countId;
    }

    public void updateHike(String name, String location, String parking, String length, String level,
                           String description, String image, int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update hikes set " +
                        "hike_name = '"+name+"', " +
                        "hike_location = '"+location+"', " +
                        "hike_parking_available = '"+parking+"', " +
                        "hike_length = '"+length+"', " +
                        "hike_level = '"+level+"', " +
                        "hike_description = '"+description+"', " +
                        "hike_image = '"+image+"' where hike_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void updateHikeInHome(int uid, int hid){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update hikes set share = '' where user_id = '"+uid+"' and hike_id = '"+hid+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void shareHike(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update hikes set share = 'yes' where hike_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public String selectShareState(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        String shareState = "";

        try{
            if(conn != null){
                String sql = "select share from hikes where hike_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    shareState = rs.getString(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return shareState;
    }

    public void updateObservation(String name, String description, String image, int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update observation set " +
                        "observation_name = '"+name+"', " +
                        "observation_description = '"+description+"'," +
                        "observation_image = '"+image+"' where observation_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void updateUsers(String username, String email, String address, String avatar, int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update users set username = '"+username+"', email = '"+email+"', address = '"+address+"' , " +
                        "avatar = '"+avatar+"' where id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void changePassword(String password, int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "update users set password = '"+password+"' where id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public String selectPasword(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        String pwd = "";

        try{
            if(conn != null){
                String sql = "select password from users where id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()){
                    pwd = rs.getString(1);
                }
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return pwd;
    }

    public void deleteHike(int hid, int uid){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "delete from hikes where hike_id = '"+hid+"' and user_id = '"+uid+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void deleteObservationByHikeID(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "delete from observation where id_of_hike = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }

    public void deleteObservation(int id){
        DatabaseConnect connect = new DatabaseConnect();
        Connection conn = connect.connection();

        try{
            if(conn != null){
                String sql = "delete from observation where observation_id = '"+id+"'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
            }
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
    }
}

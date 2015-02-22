package com.example.rs;

import com.example.model.ClientBean;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;

import javax.ws.rs.*;
import java.io.IOException;

/**
 * Created by kevin on 20/02/15.
 */
@Path( "/ftp" )
public class FtpRestService {

    private static ClientBean clientBean = new ClientBean();

    @POST
    @Path("/connect")
    @Produces("text/html")
    public String connectTheUser(@FormParam("user")final String user, @FormParam("pass")final String pass) {
        try {
            FTPClient client = new FTPClient();
            client.connect("ftpperso.free.fr", 21);
//            client.sendCommand("USER", user);
//            client.sendCommand("PASS", pass);
            if (client.login(user, pass)){
                clientBean.setUsername(user);
                clientBean.setPassword(pass);
                client.disconnect();
                return "<html><h1>You're now login</h1></html>";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "<html><h1>Fail to login</h1></html>";
    }

    @GET
    @Path("/connect")
    @Produces("text/html")
    public String connectAnonymous() {
        try {
            FTPClient client = new FTPClient();
            client.connect("localhost", 3377);
            int code = client.getReplyCode();
            client.sendCommand("USER", "anonymous");
            client.sendCommand("PASS", "");
//            if (client.login(user, pass)){
            clientBean.setUsername("anonymous");
            clientBean.setPassword("");
            client.disconnect();
//            }
            return "<html><h1>You're now login in anonymous</h1></html>";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "<html><h1>Fail to login</h1></html>";
    }

    @GET
    @Path("/quit")
    @Produces("text/html")
    public String disconnect() {
        clientBean.setUsername("");
        clientBean.setPassword("");
        return "<html><h4>You're now disconnected</h4></html>";
    }

    @GET
    @Path("/list")
    @Produces("text/html")
    public String getListFile() {
        try {
            FTPClient client = new FTPClient();
            client.connect("ftpperso.free.fr", 21);
            String temp;
            if (client.login(clientBean.getUsername(), clientBean.getPassword())){
                client.changeWorkingDirectory("/");
                FTPFile[] listeFichiers = client.listDirectories();
                temp = "<h3>"+ client.printWorkingDirectory()+"</h3><br/><ul>";
                for (FTPFile file : listeFichiers){
                    temp += "<li>"+ file.getName() +"</li>";
                }
                temp += "</ul>";
                return temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "<html><h1>Impossible to list</h1></html>";
    }
}

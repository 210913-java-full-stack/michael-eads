package utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager
{
    public static Connection conn;

    private ConnectionManager()
    {

    }

    public static Connection getConnection() throws IOException, SQLException
    {
        //if connection does not exist already, we need to create one
        if(conn == null)
        {
            Properties props = new Properties();
            //reading sensitive database information like username and password from file
            FileReader connProperties = new FileReader("src/main/resources/connections.properties");
            props.load(connProperties);
            //put together the full database address from information loaded from file
            //"jdbc:mariadb://hostname:port/databaseName?user=username&password=password"
            String connectionString = "jdbc:mariadb://"
                    + props.getProperty("hostname")
                    + ":" + props.getProperty("port")
                    + "/" + props.getProperty("databaseName")
                    + "?user=" + props.getProperty("user")
                    + "&password=" + props.getProperty("password");

            //getting the connection from the properties above
            conn = DriverManager.getConnection(connectionString);
        }

        //if connection exists already it will just return the preexisting connection
        //or when it's done with the if statement, it will return the one that we created
        return conn;
    }

}

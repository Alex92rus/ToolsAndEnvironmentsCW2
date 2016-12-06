package services;

import models.BuildSummary;
import models.Project;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by andreas on 01/12/2016.
 */
public class Database
{
    private final String db_url = "jdbc:mysql://localhost/torrent?useSSL=false";
    private final String db_user = "root";
    private final String db_password = "root";
    private final String db_table = "travistorrent_27_10_2016";

    private Connection connection;


    public Database()
    {
        try
        {
            // Register JDBC driver
            Class.forName( "com.mysql.cj.jdbc.Driver" ).newInstance();

            // Open a connection
            this.connection = DriverManager.getConnection( this.db_url, this.db_user, this.db_password );

            ConsolePrinter.print( "Connected to database" );
        }
        catch ( Exception e )
        {
            ConsolePrinter.printError( e );
            this.closeConnection();
        }
    }


    public ArrayList<BuildSummary > getBuildSummaries() throws SQLException
    {
        String query = "select gh_project_name, gh_lang, gh_by_core_team_member, tr_status, count( tr_status ) as counter " +
                        "from " + this.db_table + " " +
                        "where tr_status <> 'canceled' " +
                        "group by gh_project_name, gh_by_core_team_member, tr_status, gh_lang " +
                        "order by gh_project_name";

        ArrayList< BuildSummary > summaries = new ArrayList<>();

        try ( PreparedStatement statement = this.connection.prepareStatement( query );
              ResultSet resultSet = statement.executeQuery(); )
        {
            while( resultSet.next() )
            {
                summaries.add( new BuildSummary(
                        resultSet.getString( "gh_project_name" ),
                        resultSet.getString( "gh_lang" ),
                        resultSet.getBoolean( "gh_by_core_team_member" ),
                        resultSet.getString( "tr_status" ),
                        resultSet.getInt( "counter" )
                        )
                );
            }
        }

        return summaries;
    }


    public void closeConnection()
    {
        try
        {
            if ( this.connection != null )
            {
                this.connection.close();
                ConsolePrinter.print( "Database connection closed" );
            }
        }
        catch ( Exception e )
        {
            ConsolePrinter.printError( e );
        }
    }
}





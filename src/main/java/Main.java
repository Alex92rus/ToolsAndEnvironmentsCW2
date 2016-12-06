import services.ConsolePrinter;
import services.Database;
import services.ProjectAnalyser;

/**
 * Created by andreas on 01/12/2016.
 */
public class Main
{
    public static void main( String[] args )
    {
        // Connect to database
        ConsolePrinter.print( "\nProgram started" );
        Database db = new Database();

        try
        {
            // Creates project build summaries
            ProjectAnalyser projectBuildAggregator = new ProjectAnalyser();
            projectBuildAggregator.run( db );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            ConsolePrinter.printError( e );
        }

        // Close database connection
        db.closeConnection();
        ConsolePrinter.print( "Program terminated\n" );
    }
}

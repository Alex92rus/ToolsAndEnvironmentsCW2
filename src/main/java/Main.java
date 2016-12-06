import models.BuildSummary;
import models.Project;
import services.CSVFileWriter;
import services.ConsolePrinter;
import services.Database;
import services.ProjectProcessor;

import java.util.ArrayList;

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

        // Write header to CSV file
        CSVFileWriter.writeHeader( Project.getColumnNames() );

        try
        {
            // Get all build summaries
            ConsolePrinter.print( "Retrieve build summaries (this takes up to several minutes)" );
            ArrayList< BuildSummary > summaries = db.getBuildSummaries();
            ConsolePrinter.print( "Found summaries " + summaries.size() + " to process" );

            // Aggregate build summaries for each project
            ConsolePrinter.print( "Start aggregating build summaries for each project" );
            ProjectProcessor projectProcessor = new ProjectProcessor();
            projectProcessor.createProjects( summaries );
            ConsolePrinter.print( "Created " + projectProcessor.getTotal() + " projects" );

            // Filter relevant projects
            ConsolePrinter.print( "Start filtering projects" );
            int totalRemoved = projectProcessor.filterProjects();
            ConsolePrinter.print( "Removed " + totalRemoved + " projects" );

            // Write remaining projects to a file
            ConsolePrinter.print( "Write projects file" );
            projectProcessor.outputResultsToFile();
            ConsolePrinter.print( "Finished writing to file" );

        }
        catch( Exception e )
        {
            ConsolePrinter.printError( e );
        }

        // Close database connection
        db.closeConnection();
        ConsolePrinter.print( "Program terminated\n" );
    }
}

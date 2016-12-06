package services;

import interfaces.Processor;
import models.Build;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by andreas on 06/12/2016.
 */
public class JobAnalyser implements Processor
{
    private CSVFileWriter csvWriter;
    private HashMap< Integer, Integer > distribution;


    public JobAnalyser()
    {
        this.csvWriter = new CSVFileWriter( "job-distribution-summary.csv" );
        this.distribution = new HashMap<>();
    }


    @Override
    public void run( Database database ) throws SQLException
    {
        // Get number of jobs for each build
        ConsolePrinter.print( "Get number of jobs for each build" );
        ArrayList< Build > builds = database.getNumberOfJobsPerBuild();
        ConsolePrinter.print( "Found " + builds.size() + " build jobs in total" );

        // Create job distribution
        ConsolePrinter.print( "Create job distribution" );
        this.createDistribution( builds );
        ConsolePrinter.print( "Job distribution completed" );

        // Write results to file
        ConsolePrinter.print( "Write job distribution to file" );
        this.outputResultsToFile();
        ConsolePrinter.print( "Finished writing to file" );
    }


    private void createDistribution( ArrayList< Build > builds )
    {
        for( Build build : builds )
        {
            int jobsPerBuild = build.getTotalJobs();
            Integer value = this.distribution.get( jobsPerBuild );

            // Key does not exist yet
            if ( value == null )
            {
                this.distribution.put( jobsPerBuild, 1 );
            }
            // Key already exists, increment value by one
            else
            {
                this.distribution.put( jobsPerBuild, ++value );
            }
        }
    }


    private void outputResultsToFile()
    {
        SortedSet< Integer > keys = new TreeSet<>( this.distribution.keySet() );

        this.csvWriter.writeHeader( "Jobs Per Build, Counter" );

        for ( Integer key : keys )
        {
            Integer value = this.distribution.get (key );
            String output = key + ", " + value;
            ConsolePrinter.print( output, 5 );
            this.csvWriter.write( output );
        }
    }
}

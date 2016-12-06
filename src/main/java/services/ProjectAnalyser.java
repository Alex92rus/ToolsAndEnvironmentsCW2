package services;

import interfaces.Processor;
import models.BuildSummary;
import models.ContributionTeam;
import models.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andreas on 04/12/2016.
 */
public class ProjectAnalyser implements Processor
{
    private ArrayList< Project > projects;
    private CSVFileWriter csvWriter;


    public ProjectAnalyser()
    {
        this.projects = new ArrayList<>();
        this.csvWriter = new CSVFileWriter( "project-build-summary.csv" );
    }


    @Override
    public void run( Database database  ) throws SQLException
    {
        // Get all build summaries
        ConsolePrinter.print( "Retrieve build summaries (this takes up to several minutes)" );
        ArrayList<BuildSummary> summaries = database.getBuildSummaries();
        ConsolePrinter.print( "Found summaries " + summaries.size() + " to process" );

        // Aggregate build summaries for each project
        ConsolePrinter.print( "Start aggregating build summaries for each project" );
        this.createProjects( summaries );
        ConsolePrinter.print( "Created " + this.getTotal() + " projects" );

        // Filter relevant projects
        ConsolePrinter.print( "Start filtering projects" );
        int totalRemoved = this.filterProjects();
        ConsolePrinter.print( "Removed " + totalRemoved + " projects" );

        // Write remaining projects to a file
        ConsolePrinter.print( "Write projects to file" );
        this.outputResultsToFile();
        ConsolePrinter.print( "Finished writing to file" );
    }


    private void createProjects(ArrayList<BuildSummary> summaries )
    {
        // Stores temporary build summaries that correspond to a particular project
        ArrayList<BuildSummary> projectSummaries = new ArrayList<>();

        // Stores the nth summary - used for checking when last summary will be reached
        int counter = 0;

        // Stores current project name
        String name = summaries.get( 0 ).getProjectName();

        for ( BuildSummary summary : summaries )
        {
            // Reached last summary - add last summary and then process project metrics right away
            if ( counter == summaries.size() - 1 )
            {
                this.createProject( name, projectSummaries );
                break;
            }

            // Reached new project - process project metrics, update new project name and clear old project summaries
            if ( !summary.getProjectName().equals( name ) )
            {
                this.createProject( name, projectSummaries );
                name = summary.getProjectName();
                projectSummaries.clear();
            }

            // Add summary
            projectSummaries.add( summary );

            counter++;
        }
    }


    private int filterProjects()
    {
        Iterator< Project > iterator = this.projects.iterator();
        int counter = 0;

        while ( iterator.hasNext() )
        {
            Project project = iterator.next();
            ContributionTeam core = project.getCore();
            ContributionTeam outside = project.getOutside();

            if ( core.getTotal() == 0 || outside.getTotal() == 0 )
            {
                iterator.remove();
                counter++;
            }
        }

        return counter;
    }


    private void outputResultsToFile()
    {
        this.csvWriter.writeHeader( Project.getColumnNames() );

        for ( Project project : this.projects )
        {
            ConsolePrinter.print( project.toString(), 5 );
            this.csvWriter.write( project.toString() );
        }
    }


    private int getTotal()
    {
        return this.projects.size();
    }


    private void createProject( String name, ArrayList<BuildSummary> summaries )
    {
        // Create new project and add it to the list
        Project newProject = new Project( name );
        this.projects.add( newProject );

        // Add summaries to project and process them
        newProject.addSummaries( summaries );
    }
}

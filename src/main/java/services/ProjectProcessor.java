package services;

import models.BuildSummary;
import models.ContributionTeam;
import models.Project;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by andreas on 04/12/2016.
 */
public class ProjectProcessor
{
    private ArrayList< Project > projects = new ArrayList<>();


    public void createProjects(ArrayList< BuildSummary > summaries )
    {
        // Stores temporary build summaries that correspond to a particular project
        ArrayList< BuildSummary > projectSummaries = new ArrayList<>();

        // Stores the nth summary - used for checking when last summary will be reached
        int counter = 0;

        // Stores current project name
        String name = summaries.get( 0 ).getProjectName();

        for ( BuildSummary summary : summaries )
        {
            // Reached last summary - add last summary and then process project metrics right away
            if ( counter == summaries.size() - 1 )
            {
                this.createProject( name, summary.getLanguage(), projectSummaries );
                break;
            }

            // Reached new project - process project metrics, update new project name and clear old project summaries
            if ( !summary.getProjectName().equals( name ) )
            {
                this.createProject( name, summary.getLanguage(), projectSummaries );
                name = summary.getProjectName();
                projectSummaries.clear();
            }

            // Add summary
            projectSummaries.add( summary );

            counter++;
        }
    }


    public int filterProjects()
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


    public void outputResultsToFile()
    {
        for ( Project project : this.projects )
        {
            ConsolePrinter.print( project.toString(), 5 );
            CSVFileWriter.write( project.toString() );
        }
    }


    public int getTotal()
    {
        return this.projects.size();
    }


    private void createProject( String name, String language, ArrayList< BuildSummary > summaries )
    {
        // Create new project and add it to the list
        Project newProject = new Project( name, language );
        this.projects.add( newProject );

        // Add summaries to project and process them
        newProject.addSummaries( summaries );
    }
}

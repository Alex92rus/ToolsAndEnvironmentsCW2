package models;

import java.util.ArrayList;

/**
 * Created by andreas on 02/12/2016.
 */
public class Project
{
    private String name;
    private String language;
    private ContributionTeam core;
    private ContributionTeam outside;


    public Project( String name, String language )
    {
        this.name = name;
        this.language = language;
        this.core = new ContributionTeam();
        this.outside = new ContributionTeam();
    }


    public void addSummaries( ArrayList<BuildSummary> summaries )
    {
        for ( BuildSummary summary : summaries )
        {
            ContributionTeam team = summary.isFromCore() ? this.core : this.outside;
            team.addCounterForBuildStatus( summary.getStatus().equals( "passed" ), summary.getCounter() );
        }
    }


    public ContributionTeam getCore()
    {
        return this.core;
    }


    public ContributionTeam getOutside()
    {
        return this.outside;
    }


    public static String getColumnNames()
    {
        return "Project Name,Language," +
                ContributionTeam.getColumnNames( "CM" ) + "," +
                ContributionTeam.getColumnNames( "OM");
    }


    @Override
    public String toString()
    {
        return String.format( "%s,%s,", this.name, this.language ) +
                this.core.toString() + "," +
                this.outside.toString();
    }
}

package models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by andreas on 06/12/2016.
 */
public class Build
{
    private ArrayList< Long > jobs;


    public Build( String jobIdsString )
    {
        this.jobs = new ArrayList<>();
        this.createJobs( jobIdsString );
    }


    private void createJobs( String jobIdsString )
    {
        String[] jobIds = jobIdsString
                .replaceAll( "\\[", "" )
                .replaceAll( "\\]", "" )
                .replaceAll( "\\s", "" )
                .split( "," );

        for ( String id : jobIds )
        {
            this.jobs.add( Long.parseLong( id ) );
        }
    }


    public int getTotalJobs()
    {
        return this.jobs.size();
    }
}

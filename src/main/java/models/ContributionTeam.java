package models;

/**
 * Created by andreas on 02/12/2016.
 */
public class ContributionTeam
{
    private int passedBuilds;
    private int failedBuilds;


    public int getTotal()
    {
        return this.passedBuilds + this.failedBuilds;
    }


    public void addCounterForBuildStatus( boolean status, int counter )
    {
        if ( status )
        {
            this.passedBuilds += counter;
        }
        else
        {
            this.failedBuilds += counter;
        }
    }


    public float getBrokenBuildsPercentage()
    {
        // Division by 0 (should not occur)
        if ( this.getTotal() == 0 && this.failedBuilds != 0 )
        {
            return -1;
        }
        else
        {
            return ( ( float ) this.failedBuilds / this.getTotal() ) * 100;
        }
    }


    public static String getColumnNames( String title )
    {
        return String.format( "%s Builds,%s Passed Builds,%s Broken Builds,%s Broken Build p", title, title, title, title );
    }


    @Override
    public String toString()
    {
        return String.format(
                "%s,%s,%s,%s",
                this.getTotal(),
                this.passedBuilds,
                this.failedBuilds,
                this.getBrokenBuildsPercentage()
        );
    }
}

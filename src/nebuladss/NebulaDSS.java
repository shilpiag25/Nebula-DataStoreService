/*
 * main class for application
 */
package nebuladss;

import contrib.JettyWebServer;

/**
 * @author Jason Zerbe
 */
public class NebulaDSS implements ProgramConstants {

    private static int nd_DssPortInt = kDssDefaultPortInt;
    private static int nd_HttpPortInt = kWebAppDefaultPortInt;
    private static String nd_MasterServerUrlStr = kMasterServerBaseUrlStr;

    /**
     * help method for setting the global start values from the command line parameters
     * @param theCurrentArg String
     */
    private static void setGlobalValues(String theCurrentArg) {
        String[] currentArgArray = theCurrentArg.split(kArgumentSplit);
        String currentArgStr = currentArgArray[(currentArgArray.length - 1)];
        if ((currentArgStr != null) && (!currentArgStr.isEmpty())) {
            if (theCurrentArg.contains(kDssArgStr)) {
                nd_DssPortInt = Integer.valueOf(currentArgStr);
            } else if (theCurrentArg.contains(kHttpArgStr)) {
                nd_HttpPortInt = Integer.valueOf(currentArgStr);
            } else if (theCurrentArg.contains(kMasterServerArgStr)) {
                nd_MasterServerUrlStr = currentArgStr;
            }
        }
    }

    /**
     * the application execution start point
     * @param args String[]
     */
    public static void main(String[] args) {
        for (String currentArg : args) {
            if (currentArg.contains("-h")) {
                System.out.println(kUsageStr);
                System.exit(0);
            } else if (currentArg.contains(kDssArgStr)) {
                setGlobalValues(currentArg);
            } else if (currentArg.contains(kHttpArgStr)) {
                setGlobalValues(currentArg);
            } else if (currentArg.contains(kMasterServerArgStr)) {
                setGlobalValues(currentArg);
            }
        } //done processing arguments

        //set bootstrap master server URL
        MasterServer aMasterServer = MasterServer.getInstance();
        aMasterServer.setMasterSeverUrlStr(nd_MasterServerUrlStr);

        //start jetty with servlets that accept GET/POST for file management
        JettyWebServer aJettyWebServer = JettyWebServer.getInstance(nd_HttpPortInt);
        aJettyWebServer.startServer();
        
        //tell master server about self - HTTP and Dss control port

        //open up control console
        //for more: http://www.javapractices.com/topic/TopicAction.do?Id=79
    }
}

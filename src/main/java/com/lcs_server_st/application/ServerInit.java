package com.lcs_server_st.application;

/*
    Entry point for program
    Initializes the server
    Parses cmdline arguments

 */

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcs_server_st.models.config.ConfigFile;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class ServerInit
{
    private static final String usage = "\nUsage:" +
            "\n-c <path> => config file location(debug)";

    private static final String CONFIG_PATH = "config.json";

    //default options for those in config file
    private static final HashMap<String, Integer> options = new HashMap<>();
    static
    {
        options.put("thread_count", 24);
        options.put("port", 5349);
    }

    public static void main(String[] args)
    {
        //turns off excess logging from Javalin
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");

        loadConfig( getConfigPath(args) );

        Router.getRouter().start(options.get("port"), options.get("thread_count"));
    }

    //attempt to load the values stored in the config file
    private static void loadConfig(String path)
    {
        File config = new File(path);

        if(!config.exists())
        {
            System.out.println("Could not locate config file, running with default configuration");
            return;
        }

        try
        {
            String configRaw = new String(Files.readAllBytes(config.toPath()));
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                    .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true);

            ConfigFile configFile = objectMapper.readValue(configRaw, ConfigFile.class);

            int threadCount = configFile.getThread_count();
            int port = configFile.getPort();

            if(threadCount >= 8 && threadCount <= 24)
            {
                options.put("thread_count", threadCount);
            }
            else
            {
                System.out.println("Invalid thread count found, setting to default");
            }

            options.put("port", port);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error reading config file, running with default configuration");
        }
    }

    //get config file path, if passed in (used for debug)
    private static String getConfigPath(String[] args)
    {

        if(args.length == 0)
        {
            return CONFIG_PATH;
        }
        else if(args.length != 2)
        {
           System.out.println(usage);
           System.exit(-1);
        }

        String opt = args[0];
        String arg = args[1];

        if(!opt.equals("-c"))
        {
            System.out.println(usage);
            System.exit(-1);
        }

        return arg;

    }

}

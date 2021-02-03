package com.lcs_server_st.controllers;

import com.lcs_server_st.models.Result;
import com.lcs_server_st.util.RequestCompletionException;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
/*
    loads the home page from storage once,
    will be cached as long as the server keeps running

 */
public class HomePageController
{
    private static final String PATH = "/views/index.html";
    private static final AtomicReference<String> CACHED_PAGE = new AtomicReference<>(null);

    public HomePageController(){}

    public Result getPageSrc() throws RequestCompletionException
    {
        Result result = new Result();

        try
        {
            System.out.println("loading page");

            String pageData;

            if((pageData = CACHED_PAGE.get()) != null)
            {
                System.out.println("Returning cached page");
                result.setValue( pageData);
            }
            else
            {
                System.out.println("Loading from storage");
                InputStream pageInputStream = HomePageController.class.getResourceAsStream(PATH);

                if(pageInputStream == null)
                {
                    pageInputStream = HomePageController.class.getClassLoader().getResourceAsStream(PATH);
                }


                byte[] pageBytes = new byte[pageInputStream.available()];
                pageInputStream.read(pageBytes);

                pageData = new String(pageBytes);
                pageInputStream.close();

                CACHED_PAGE.set(pageData);
                result.setValue( pageData );
            }

            result.setCode(200);
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RequestCompletionException(
                    "whoops, something went wrong",
                    500);
        }
    }
}

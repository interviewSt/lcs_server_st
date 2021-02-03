package com.lcs_server_st.application;

import com.lcs_server_st.controllers.HomePageController;
import com.lcs_server_st.controllers.LcsController;
import com.lcs_server_st.models.Result;
import com.lcs_server_st.util.RequestCompletionException;
import io.javalin.Javalin;
import kotlin.KotlinNullPointerException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
    Entry point for requests
    routes each request to appropriate handler

    Each request is handled on a separate non-blocking thread

*/
class Router
{
    private static Router routerInstance = null;
    private static Javalin javalin;

    private static ExecutorService pool;

    private Router(){}

    static Router getRouter()
    {
        if(routerInstance == null)
        {
            routerInstance = new Router();
            init();
        }

        return routerInstance;
    }

    //create the javalin instance and specify routes
    private static void init()
    {
        javalin = Javalin.create();

        //route for home page
        javalin.get("/", context ->
            context.result(
            CompletableFuture.supplyAsync(() ->
            {
                try
                {
                    Result result = new HomePageController().getPageSrc();

                    context.status(result.getCode());
                    context.contentType("text/html");

                    return result.getValue();
                }
                catch (RequestCompletionException e)
                {
                    System.out.println("Error getting page " + e.getErrorMessage());
                    e.printStackTrace();

                    context.contentType("text/plain");
                    context.status(e.getCode());
                    return e.getErrorMessage();
                }
            }, pool)
        ));

        //route for lcs
        javalin.post("/lcs", context ->
            context.result(
            CompletableFuture.supplyAsync(() ->
            {
                System.out.println("got lcs request");
                context.contentType("application/json");

                try
                {
                    Result result = new LcsController().handle(context.body());

                    context.status(result.getCode());
                    return result.getValue();
                }
                catch (RequestCompletionException e)
                {
                    System.out.println(e.getErrorMessage());
                    e.printStackTrace();

                    context.contentType("text/plain");
                    context.status(e.getCode());
                    return e.getErrorMessage();
                }

            }, pool)));

        //general, catch-all exception catching
        javalin.exception(KotlinNullPointerException.class, (e, ctx) ->
                ctx.status(500));
    }

    void start(int port, int threadCount)
    {
        System.out.println(String.format("Initializing with %d threads on port %d", threadCount, port));
        pool = Executors.newFixedThreadPool(threadCount);
        javalin.start(port);
    }
}

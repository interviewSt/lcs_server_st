## About
This is a website and server that calculates Longest Common String from a supplied list of words   



## REQUIRMENTS

1. Java 8
2. maven for building
3. python for testing
4. (optional) python request module for testing- can be installed with a script

## BUILDING
`git clone https://github.com/interviewSt/lcs_server_st`

`cd lcs_server_st`

`./build.sh` 


## CONFIG
Server port and thread_count can be configured in
*config.json*

thread_count must be between 8 and 24

Server must be restarted for new config to take into effect


## RUNNING
`./run.sh`


## TESTING
LCS payloads can be added to *test_payload.json*

Once the server is running, use `python3 test.py` to run POSTs against it with the payloads

Or POST directly to http://localhost:{port}/lcs

*if you are having trouble running test.py, use `./install_requests.py`*

*test_py* will POST each payload contained in test_payload.json one at a time and 
will print out a response

## WEBSITE

A website is baked in and can be accessed at http://localhost:{port}/
    
On startup, the server will also print out a usuable link

*Requests made by the website to the server are curated.*
*To test edge cases, please use test.py or POST directly*

#### white spaces will be ignored

## Server Code Structure
```
src/main/
    java/com.lcs_server_st
        application/
            ServerInit (entry point)
            Router
         controllers/
            (request handlers)
         models/ 
            (data structures)
         util/ 
            (misc)
     resources/
        views/
            index.html (home page)
``` 

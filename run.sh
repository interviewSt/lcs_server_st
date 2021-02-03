#!/bin/bash

JAR=target/lcs_server_st.jar
if [ ! -f "$JAR" ]; 
then
    ./build.sh
fi

java -jar target/lcs_server_st.jar -c config.json

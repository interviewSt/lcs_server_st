#!/bin/bash

pip=$(python3 -m pip --version)

if [[ "$pip" = pip* ]];
then
        echo "pip already installed"
else
        curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
        python3 get-pip.py
fi

python3 -m pip install requests

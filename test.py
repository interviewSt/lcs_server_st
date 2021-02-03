import requests
import json
import sys

port = 5349
payloads = {}

try:
    with open('config.json') as config_file:
        config = json.load(config_file)
        port = config['port']

except:
    print("couldn't parse config file, running with default port")


try:
    with open('test_payload.json') as payload_file:
        payloads = json.load(payload_file)

except:
    print("couldn't parse payload file")
    sys.exit()

for payload in payloads['payloads']:
    try:
        r = requests.post("http://localhost:{}/lcs".format(port), data = json.dumps(payload) )
        body = r.text
   
        if str(r.headers['Content-Type']).startswith('text'):
            print("ERROR\n")
            print(r.status_code)
            print(body )
        
        else:
            body = json.loads(body)
            print("\nLCS LIST\n")
            for lcs in body['lcs']:
                print(lcs['value'])

        print("\n*************************\n")
    
    except:
        print("Couldn't connect to server, is it running?")
        sys.exit()


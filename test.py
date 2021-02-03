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
    r = {}
    body = {}
    try:
        r = requests.post("http://localhost:{}/lcs".format(port), data = json.dumps(payload) )
        body = r.text
  
    except:
        print("Couldn't connect to server, is it running?")
        sys.exit()

    print("Status {}".format( r.status_code))
    if str(r.headers['Content-Type']).startswith('text'):
        print("ERROR\n")
        print(body)
    
    else:
        print("RAW BODY\n")
        print("{}\n".format(body))
        body = json.loads(body)
 
        print("LCS LIST")
        for lcs in body['lcs']:
            print(lcs['value'])

    print("\n*************************\n")
    



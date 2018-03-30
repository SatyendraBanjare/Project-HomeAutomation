import RPi.GPIO as GPIO
import serial

import requests
import time

ser = serial.Serial('/dev/ttyUSB0', 9600, timeout = 0.5)
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(5,GPIO.OUT)
GPIO.setup(6,GPIO.OUT)
GPIO.setup(13,GPIO.OUT)
GPIO.setup(26,GPIO.OUT)

url = "YOUR_PROJECT_SITE/api/list/"

headers = {
    'Authorization': "Token XYXYXYXYX_THE_AUTH_TOKEN_FOR_A_USER ",
    'Cache-Control': "no-cache",
    }

while True:
    #try:
    response = requests.request("GET", url, headers=headers).json()
    #except ConnectionError:
     #   pass
    mainswitch=response[0]['MainSwitch']
    item1=response[0]['Item1Bool']
    item2=response[0]['Item2Bool']
    item3=response[0]['Item3Bool']
    item4=response[0]['Item4Bool']
    item5=response[0]['Item5Bool']
    item6=response[0]['Item6Bool']
    
    if mainswitch==True:
        GPIO.output(5,item1)
        GPIO.output(6,item2)
        GPIO.output(13,item3)
        GPIO.output(26,item4)
        if item5 == True and item6 == False:
            ser.write('1')
        if item5 == False and item6 == True:
            ser.write('2')
        if item5 == False and item6 == False:
            ser.write('3')
        if item5 == True and item6 == True:
            ser.write('4')
        time.sleep(0.05)
        
    else:
        GPIO.output(5,False)
        GPIO.output(6,False)
        GPIO.output(13,False)
        GPIO.output(26,False)
        ser.write('3')
        time.sleep(0.05)

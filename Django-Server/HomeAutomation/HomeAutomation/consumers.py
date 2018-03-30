from channels import Group
from channels.sessions import channel_session

def push_data(SomeString):
    Group("home").send({'text': SomeString })

#@channel_session
def ws_receive(message):
    # ASGI WebSocket packet-received and send-packet message types
    # both have a "text" key for their textual data.
    print("Received!!" + message['text'])

#@channel_session
def ws_connect(message):
    global push_string, flag
    print (message)
    print("Someone connected.")
    path = message['path']
    print (path)                                                     

    if (path == '/home/'):
        print("Adding new user to  group")
        Group("home").add(message.reply_channel)                             
        message.reply_channel.send({                                            # Reply to individual directly
           "text": "You're connected to group :) ","accept": True,
        })
        
        Group("home").send({"text": "yjxychkgvjhbkjbgl",})
        flag = False
    else:
        print("Strange connector!!")



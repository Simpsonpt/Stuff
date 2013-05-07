#Port knocking Script
import socket
from time import sleep

HOST = '194.65.94.51'
PORT = 3137

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

data = s.recv(100)
s.sendall('a5aa7bbde30a822f425b9104c2c207c215efb4d2663721af3e3132131791a5e4'+chr(0x0a))
data = s.recv(1000)
data = s.recv(1000)
s.close()
ports = data.split(",")
print ports
for port in ports:
	print "Port %s" % (port)
	sleep(1)
	st = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	st.connect((HOST, int(port)))
	st.close()

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, 3616))

print s.recv(1000)
print s.recv(1000)
print s.recv(1000)
print s.recv(1000)

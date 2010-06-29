#-*- coding:cp1252 -*-
# Code Developed by Renato Rodrigues
# rmbr@student.dei.uc.pt 
# (06/2010)
from string import *
from random import *

def main():
	string=raw_input("Winning Phrase: ")
	calc(string)
	return 0
	
def calc(string):
	numeros=[]
	for i in range(len(string)):
		temp=(hash(string[i])+59)%50
		if(numeros.count(temp)==0 and temp != 0):
			numeros.append(temp)
	
	listaFinal=[]
	if(len(numeros)>=7):
		for i in range(7):
			var=choice(numeros)
			listaFinal.append(var)
			numeros.remove(var)
			
		print "Winner Key: ",listaFinal[0]," ",listaFinal[1]," ",listaFinal[2]," ",listaFinal[3]," ",listaFinal[4]," - ",listaFinal[5]%9," ",listaFinal[6]%9
	else:
		print "Sentence Too Short!"
		
	

if __name__ == '__main__':
	main()

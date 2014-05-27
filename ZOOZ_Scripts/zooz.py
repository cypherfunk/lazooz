#!/usr/bin/python

import MySQLdb
import datetime
import json
from generateTX00 import *




def getAddressForZOOZ(l_pkid):
        #get address to assign ZOOZ to
        print "getAddressForZOOZ '%d'" % (l_pkid)
        sql = "SELECT publickey FROM profiles WHERE pkid = '%d'" % (l_pkid)
        try:
           cursor.execute(sql)
           address = cursor.fetchone()
        except:
           print "error fetch pub key"
        return address    

def updateZOOZPayment(l_val,l_pkid):
	print "Update POL val '%d' for profileid '%d'" % (l_val,l_pkid)
        l_sql = "UPDATE locations SET POL = '%d' WHERE pkid = '%d'" % (l_val,l_pkid)
        try:
           # Execute the SQL command
           cursor.execute(l_sql)
           # Commit your changes in the database
           db.commit()
        except:
           print "error update pol"
           db.rollback()
        return


def SendZOOZ(l_profileid,l_amount,l_pkid):
     # Prepare SQL query to UPDATE required records
        address=getAddressForZOOZ(l_profileid)
        
        t_amount = l_amount *  0.00000001
        
        amount_str = '%.8f' % (t_amount)
        
        #print Decimal(t_amount)

        data =  { 'transaction_from': '1Htqiy3JBG9wYyFJ6utJxzcj6BneKhHZ2M' ,\
                  'transaction_to': address[0],   \
                  'msc_send_amt':  t_amount , \
                  'currency': '2147483670',  \
                  'from_private_key': '5JWz3XMRnVL5hvSwtBhGGKvTTzun6CNwPvyZUoXR2wJvwveTASU' \
                  } 
        json_str = json.dumps(data)
        if (generateTX00(json_str) < 0):
          print "not enoghe balance"
          return -1
        

        print "send '%d' ZOOZ to profileid '%d' with address '%s'" %(l_amount,l_profileid,address)
        sql = "UPDATE profiles SET zooz = zooz + '%d' WHERE pkid = '%d'" % (l_amount,l_profileid)
	try:
   # Execute the SQL command
		cursor.execute(sql)
   # Commit your changes in the database
		db.commit()
                print "commit update"
	except:
   # Rollback in case there is any error
		print "error update prfile"
		db.rollback()
	return
 

# Open database connection
db = MySQLdb.connect("localhost","root","root","mobilelocation" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

# execute SQL query using execute() method.
cursor.execute("SELECT VERSION()")

# Fetch a single row using fetchone() method.
data = cursor.fetchone()
print "Database version : %s " % data


# Prepare SQL query to GET a record from the database.
sql = "SELECT * FROM locations WHERE POL = '%d'" % (1)
#updateZOOZPayment()
try:
   # Execute the SQL command
   cursor.execute(sql)
 
   # Fetch all the rows in a list of lists.
   results = cursor.fetchall()
   
   for row in results:
      pkid = row[0]
      profileid = row[1]
      latitide = row[2]
      longitude = row[3]
      last_datetime = row[4]
#      POL = raw[5]
      print "pkid=%d %d %ld %ld " % (pkid,profileid,latitide,longitude)
      if (SendZOOZ(profileid,1,pkid)==0):
       updateZOOZPayment(2,pkid)
	
except:
   print "Error: unable to fecth data"

# disconnect from server
db.close()

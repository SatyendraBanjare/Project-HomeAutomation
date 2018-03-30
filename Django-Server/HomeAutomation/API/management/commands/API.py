from channels import Group
from django.core.management import BaseCommand
#from ...models import 

flag = True
Sample_String = "hello this is cool"


class Command(BaseCommand):

	def __init__(self):
		self.flag = True
		self.Sample_String = "madarchad"

	
	def handle(self, *args, **options):	
		while True:
			if (self.flag == True):
				Group("home").send({'text': self.Sample_String })
				print ("sending")
				self.flag = False
				


	def pushdata(self,stringname):
		#print (stringname)
		#print ("iubviouwebvweslhvgsdivbsdivbsdivbuo")
		self.Sample_String = stringname
		self.flag = True
		#print (Sample_String)
		self.check()

	def check(self):
		print(self.flag,self.Sample_String)

						
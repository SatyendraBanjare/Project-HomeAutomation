from django.shortcuts import render
from rest_framework import generics
from rest_framework.mixins import UpdateModelMixin
from .models import UserModel
from .serializers import ListSerializer , UserSerializer , UpdateSerializer , IdSerializer
from django.contrib.auth.models import User
from django.http import HttpResponse

class UserCreate(generics.CreateAPIView):
	serializer_class = UserSerializer
	authentication_classes = ()
	permission_classes = ()

class GetId(generics.ListCreateAPIView):
	serializer_class = IdSerializer
	def get_queryset(self):
 		requesting_user = self.request.user
 		return UserModel.objects.filter(user=requesting_user)
	
		
class UserDetail(generics.RetrieveAPIView):
	queryset = User.objects.all()
	serializer_class = UserSerializer

class SimpleList(generics.ListCreateAPIView):
	serializer_class = ListSerializer
	def get_queryset(self):
 		requesting_user = self.request.user
 		return UserModel.objects.filter(user=requesting_user)
'''
class UpdateInfo(generics.UpdateAPIView):
	queryset = UserModel.objects.all()
	serializer_class = SimpleSerializer
	authentication_classes = ()
	permission_classes = ()
	def update(self, request, *args, **kwargs):
		instance = self.get_object()
		if ((instance.user) != (self.request.user)):
			return "not allowed"
		if ((instance.user) == (self.request.user)):
			instance.Item1Bool= request.data.get('Item1Bool')
			instance.Item1Value = request.data.get('Item1Value')
			instance.save()
			serializer = self.get_serializer(instance)
			serializer.is_valid(raise_exception=True)
			self.perform_update(serializer)
			return Response(serializer.data)
'''


class PartialUpdateView(generics.GenericAPIView, UpdateModelMixin):
    queryset = UserModel.objects.all()
    serializer_class = UpdateSerializer

    def put(self, request, *args, **kwargs):
    	instance = self.get_object()
    	if ((instance.user) != (self.request.user)):
    		return HttpResponse("notallowed")
    	else :
    		return self.partial_update(request, *args, **kwargs)

		
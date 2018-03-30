from django.shortcuts import render
from rest_framework import generics
from rest_framework.mixins import UpdateModelMixin
from .models import UserModel
from .serializers import ListSerializer , UserSerializer , UpdateSerializer , IdSerializer
from django.contrib.auth.models import User
from django.http import HttpResponse
from rest_framework.views import APIView
from rest_framework.renderers import TemplateHTMLRenderer
from rest_framework.response import Response
from django.contrib.auth.decorators import login_required
from rest_framework.permissions import IsAuthenticated
from django.contrib.auth.mixins import LoginRequiredMixin
from HomeAutomation import consumers
import hashlib

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
	http_method_names = ['get', 'head']
	def get_queryset(self):
 		requesting_user = self.request.user
 		return UserModel.objects.filter(user=requesting_user)

#@login_required(login_url='/accounts/login/')
class HomeView(LoginRequiredMixin,APIView):
	login_url = '/accounts/login/'
	renderer_classes = [TemplateHTMLRenderer]
	permission_classes = (IsAuthenticated,)
	template_name = 'home.html'
	def get(self, request):
		requesting_user = self.request.user
		queryset = UserModel.objects.filter(user=requesting_user)
		print(hashlib.sha256(str(self.request.user ).encode()).hexdigest())
		return Response({'lists': queryset})
	
def HomeV(request):
	return render(request, "home.html")
	
	

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


class PartialUpdateView(generics.GenericAPIView):
    queryset = UserModel.objects.all()
    serializer_class = UpdateSerializer

    def get(self, request, *args, **kwargs):
    	instance = self.get_object()
    	if ((instance.user) != (self.request.user)) :
    		return HttpResponse("notallowed")
    	else:
    		queryset = UserModel.objects.all()
    		serializer = UpdateSerializer(queryset)
    		return Response(serializer.data)
	

    def put(self, request, *args, **kwargs):
    	instance = self.get_object()
    	if ((instance.user) != (self.request.user)):
    		return HttpResponse("notallowed")
    	else :
    		return self.partial_update(request, *args, **kwargs)

    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        self.perform_update(serializer)

        if getattr(instance, '_prefetched_objects_cache', None):
            # If 'prefetch_related' has been applied to a queryset, we need to
            # forcibly invalidate the prefetch cache on the instance.
            instance._prefetched_objects_cache = {}

        return Response(serializer.data)

    def perform_update(self, serializer):
        serializer.save()

    def partial_update(self, request, *args, **kwargs):
        kwargs['partial'] = True
        return self.update(request, *args, **kwargs)		
            
		
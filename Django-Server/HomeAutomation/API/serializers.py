from rest_framework import serializers
from django.contrib.auth.models import User

from .models import UserModel


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'email', 'password')
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        USER = User(
            email = validated_data["email"],
            username = validated_data["username"]
        )
        USER.set_password(validated_data["password"])
        USER.save()
        UserModel.objects.create(user = USER)
        return USER


class ListSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel
        fields = ('id','label','user_name','user','MainSwitch','Item1Bool',
            'Item1Value','Item2Bool','Item2Value'
            ,'Item3Bool','Item3Value','Item4Bool','Item4Value',
            'Item5Bool','Item5Value','Item6Bool','Item6Value',
            'Item7Bool','Item7Value','Item8Bool','Item8Value',
            'Item9Bool','Item9Value','Item10Bool','Item10Value',)

class IdSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel
        fields = ('id','label',)

class UpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel
        fields = ('Item1Bool','Item1Value','Item2Bool','Item2Value'
            ,'Item3Bool','Item3Value','Item4Bool','Item4Value',
            'Item5Bool','Item5Value','Item6Bool','Item6Value',
            'Item7Bool','Item7Value','Item8Bool','Item8Value',
            'Item9Bool','Item9Value','Item10Bool','Item10Value',)
            
                        
            

        
from django.conf import settings
from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login, logout

def logout_user(request):
    logout(request)
    return render(request, 'Accounts/logout.html', {})


def login_form(request):
    return render(request, 'Accounts/login.html', {})

def login_user(request):
    username = request.POST.get('username')
    password = request.POST.get('password')
    user = authenticate(username=username, password=password)
    if user is not None:
        # the password verified for the user
        if user.is_active:
            login(request, user)
            return redirect('/api/list')
    return redirect(settings.LOGIN_URL, request)

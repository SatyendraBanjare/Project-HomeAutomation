from django.conf.urls import url
from django.conf import settings
from django.conf.urls.static import static

from . import views


urlpatterns = [
    # Session Login
    url(r'^login/$', views.login_user, name='login'),
    url(r'^logout/$', views.logout_user, name='logout'),
    url(r'^auth/$',  views.login_form, name='login_form'),
] + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)

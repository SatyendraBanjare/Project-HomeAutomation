from django.conf.urls import url
from django.conf import settings
from django.conf.urls.static import static
from rest_framework.authtoken import views as drfview
from . import views

from rest_framework import routers

router = routers.DefaultRouter()
router.register(r'^list/', views.SimpleList,base_name='list')


urlpatterns = [
    url(r'^list/$', views.SimpleList.as_view()),
    url(r'^create_user/$', views.UserCreate.as_view()),
    url(r'^update/(?P<pk>\d+)/$',views.PartialUpdateView.as_view(), name='update'),
    url(r'^users/(?P<pk>[0-9]+)/$', views.UserDetail.as_view()),
    url(r'^get-auth-token/', drfview.obtain_auth_token),
    url(r'^get-id/',views.GetId.as_view()),
] + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)




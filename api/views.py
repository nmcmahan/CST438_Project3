from .models import Users, Items
from rest_framework import permissions
from rest_framework import viewsets
from .serializers import UserSerializer, ItemSerializer
from django_filters.rest_framework import DjangoFilterBackend
# Create your views here.


class UserViewSet(viewsets.ModelViewSet):
    queryset = Users.objects.all()
    serializer_class = UserSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_fields = ['username']

class ItemViewSet(viewsets.ModelViewSet):
    queryset = Items.objects.all()
    serializer_class = ItemSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_fields = ['likes', 'category', 'user_id', 'creator']
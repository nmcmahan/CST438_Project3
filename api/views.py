from .models import Users, Items
from rest_framework import permissions
from rest_framework import viewsets
from .serializers import UserSerializer, ItemSerializer
# Create your views here.


class UserViewSet(viewsets.ModelViewSet):
    queryset = Users.objects.all()
    serializer_class = UserSerializer

class ItemViewSet(viewsets.ModelViewSet):
    queryset = Items.objects.all()
    serializer_class = ItemSerializer
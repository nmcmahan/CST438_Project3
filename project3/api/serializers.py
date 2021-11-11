from api.models import Users, Items
from rest_framework import serializers

class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Users
        fields = ['username', 'password']

class ItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Items
        fields = ['user_id', 'url', 'name', 'description', 'likes']
from django.db import models

# Create your models here.
class Users(models.Model):
    username = models.CharField(max_length=200, unique=True);
    password = models.CharField(max_length=200);


class Items(models.Model):
    user_id = models.ForeignKey(Users, on_delete=models.CASCADE);
    url = models.CharField(max_length=200);
    name = models.CharField(max_length=200);
    description = models.CharField(max_length=500);
    likes = models.IntegerField(default=0);
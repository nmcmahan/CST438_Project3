from django.db import models

# Create your models here.
class Users(models.Model):
    username = models.CharField(max_length=200, unique=True);
    password = models.CharField(max_length=200);


class Items(models.Model):
    user_id = models.ForeignKey(Users, on_delete=models.CASCADE);
    username = models.CharField(max_length=200);
    img_url = models.CharField(max_length=200);
    title = models.CharField(max_length=200);
    category = models.CharField(max_length=200);
    likes = models.IntegerField(default=0);
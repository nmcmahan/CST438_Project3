from django.db import models

# Create your models here.
class Users(models.Model):
    username = models.CharField(max_length=200);
    password = models.CharField(max_length=200);
    constraints = [
        models.UniqueConstraint(fields=['username'], name='unique username')
    ]


class Items(models.Model):
    user_id = models.ForeignKey(Users, on_delete=models.CASCADE);
    url = models.CharField(max_length=200);
    name = models.CharField(max_length=200);
    description = models.CharField(max_length=500);
    likes = models.IntegerField(default=0);
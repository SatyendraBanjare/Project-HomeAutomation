from django.db import models
from django.contrib.auth.models import User
from django.core.validators import MaxValueValidator, MinValueValidator
#from django.template.defaultfilters import slugify

class UserModel(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE,editable=True)
    user_name = models.CharField(null=False,default="helloworld",unique=True,max_length=50)
    MainSwitch = models.BooleanField(default=0)
    Item1Bool = models.BooleanField(default=0)
    Item1Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item2Bool = models.BooleanField(default=0)
    Item2Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item3Bool = models.BooleanField(default=0)
    Item3Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item4Bool = models.BooleanField(default=0)
    Item4Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item5Bool = models.BooleanField(default=0)
    Item5Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item6Bool = models.BooleanField(default=0)
    Item6Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item7Bool = models.BooleanField(default=0)
    Item7Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item8Bool = models.BooleanField(default=0)
    Item8Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item9Bool = models.BooleanField(default=0)
    Item9Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    Item10Bool = models.BooleanField(default=0)
    Item10Value = models.IntegerField(default=0,validators=[
            MaxValueValidator(100),
            MinValueValidator(0)
        ])
    def save(self, *args, **kwargs):
        self.user_name = self.user.username
        super(UserModel, self).save(*args, **kwargs)
    '''
    slug = models.SlugField(unique=True,null=True)

    def save(self, *args, **kwargs):
        self.slug = slugify(self.user.username + "001")
        super(UserModel, self).save(*args, **kwargs)
    
    slug = models.Charfield(max_length=150, unique=True)
    def save(self):
    	super(MyModel, self).save()
    self.slug = '%i-%s' % (
    self.id, slugify(self.user.username)
    )
    super(MyModel, self).save()
    '''
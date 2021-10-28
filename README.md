# CST438_Project3

### Contributors:
Niel McMahan  
Steven Schreiber  
Nicholas Anderson

### Desctiption:
This project will be an image sharing application running on django, with an android frontend. It will allow users to upload their own images, and sort them by category. Then, users can view a list of all images, and from there access the other app functions, such as following other users, liking images, or filtering the images by category.

### Sitemap:


### ERD:


### Tech Stack:
Django->SQLite->Android

### API Endpoints
Create new user account
  POST: [url]/newuser?username={username}&password={password}
  
Log in to account
  POST: [url]/login?username={username}&password={password}
  
Log out of account
  POST/GET [url]/logout?username={username}
 
Delete account
  DELETE [url]/logout?username={username}
  
List all items
  GET [URL]/items
 
Show a specific list
  GET [URL]/items?list={list name || list ID}
  OR
  GET [URL]/items?user={user id}&list={list name || list ID}
  
Add new items
  POST [URL]/items?item_name={item name}&url={url}...
  
Remove items
  DELETE [URL]/items?item_name={item id}

Update items
  PATCH [URL]/items?item_name={item id}

# The CRM Service

REST API to manage customer data for a small shop.


## Getting Started

clone the project in local machine  with the following:

```
git clone https://github.com/elrusoft/crm-service.git
```

# How to use


Open http://localhost:8080/ in your browser or some HTTP client (like a Postman).

in this project two tables are created, one is for users and the other is customer


## Users

To create, delete, update, list user and change the role status we will use the following:

### Create

To create the user, we must use the post method in this way

```
http://localhost:8080/api/user/{idAdmin}

```
you must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and send a json with the following structure:

```
{
        "email": "mikhail.polozhaev@gmail.com",
        "username": "elrusoft",
        "password": "1234",
}
```

### Delete

To update the user, is similar to creating user but using the PUT method

```
http://localhost:8080/api/{idAdmin}/user/{id}

```
you must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and "id" is the id of the user that you want to erase:



### Update

To update the user, is similar to creating user but using the PUT method

```
http://localhost:8080/api/user/{idAdmin}

```
you must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and send a json with the following structure:

```
{
        "email": "mikhail.polozhaev@gmail.com",
        "username": "elrusoft",
        "password": "1234",
}
```

### List 

to list the user you should only use the following:

```
http://localhost:8080/api/user/
```

if you only want to bring the information of a single user:

```
http://localhost:8080/api/user/{username}
```
passing the user you want to see the information in "username".








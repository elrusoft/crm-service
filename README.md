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

to make requests a basic authentication is used, a username and password must be provided. At the moment these users are static, in the future it will be possible to use the users created in the database. Users can be changed in the SpringSecurityConfig file in the part of:

```
	@SuppressWarnings("deprecation")
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
				auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("root").password("123456").roles("ADMIN").and().withUser("user").password("123456").roles("USER");
				
        }
```	


## Users

To create, delete, update, list user and change the role status we will use the following:

### Create

To create the user, we must use the post method in this way

```
http://localhost:8080/api/user/{idAdmin}

```
You must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and send a json with the following structure:

```
{
        "email": "mikhail.polozhaev@gmail.com",
        "username": "elrusoft",
        "password": "1234",
}
```

### Delete

To update the user, is similar to creating user but using the DELETE method

```
http://localhost:8080/api/{idAdmin}/user/{id}

```
You must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and "id" is the id of the user that you want to erase:



### Update

To update the user, is similar to creating user but using the PUT method

```
http://localhost:8080/api/user/{idAdmin}

```
You must provide the id of the user admin in "idAdmin", in this way the system checks if you have permission to create user , and send a json with the following structure:

```
{
        "email": "mikhail.polozhaev@gmail.com",
        "username": "elrusoft",
        "password": "1234",
}
```

### List 

To list the user, with the GET method you should only use the following:

```
http://localhost:8080/api/user/
```

If you only want to bring the information of a single user:

```
http://localhost:8080/api/user?{username}
```
passing the user you want to see the information in "username".

### Change to admin

If you want to change a user to be an admin, with the PUT method  you must use the following:

```
http://localhost:8080/api/user/{idAdmin}/{iduser}
```

You must pass the id ("idAdmin") of the user admin and the id ("iduser") of the user that you want to change to admin


## Customers

Customer has requests to create, update, delete, list, upload image.

###

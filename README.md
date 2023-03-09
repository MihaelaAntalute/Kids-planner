# Hi, I'm Mihaela! 👋
And here you can find the documentation of the Kids-planner project


## 🚀 About Me
I'm a back-end software developer, passionate about solving problems using technology


## 🛠 Skills
Java, OOP, Spring Boot, Rest APIs, MySQL


## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/MihaelaAntalute/Kids-planner.git/)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mihaela-antalute/)
[![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)](https://agile-ocean-81430.herokuapp.com/swagger-ui/)
![YouTube Music](https://img.shields.io/badge/YouTube_Music-FF0000?style=for-the-badge&logo=youtube-music&logoColor=white)

# Kids-planner
This application helps parents manage their children's activities


## Features

As a parent, I can:
- Add child
- Get all users names(kids names)
- Update child
- Delete child
- Add activity to child
- Evaluate activity
- Update activity
- Delete activity
- To activity type "homework",the parent check if the homework is done and add is done
- Add wish to wish list child
- Get wish by user(child)
- Delete wish
- Update wish
- Evaluate period and send mail to child
- Get period
- Get all periods
- Get period by user name

As a child, I can:

- To activity type "homework",the child do the homework and add is done
- Add wish to wish list
- Update wish
- Delete wish
- Get all wishes


## Built with
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

## Demo

https://webm.to/results/5d4db32794604699a9eb3f6055011ec6?lang=ro


## API Reference

#### Add child

```http
  POST /child/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of child to be added |

Request body example:
```json
    {
     "name": "Miriam"
     "birthday":"2010-11-25",
     "email":"fdrghyjj@gmail.com",
     "role":"CHILD"
    }
```  


#### Add activity to child

```http
  POST /activity/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of activity to be added |


Request body example:
```json
    {
     "username":"thalia",
     "periodId":"102",
     "activityType":"homework",
     "description":"tema culegere matematica"
    }
```  

#### Add wish

```http
  POST /activity/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of wish to be added |


Request body example:
```json
    {
     "username":"thalia",
     "text":"stat o ora la televizor"
    }
```  

#### Add is check homework by parent

```http
  POST /activity/isDoneParent
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of activity to be added |


Request body example:
```json
    {
    "periodActivityId":"152",
    "isDoneParent":"true"
    }
```  
#### Evaluate activity

```http
  POST /activity/evaluate
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of activity to be added |


Request body example:
```json
    {
    "activityId":"153",
    "periodId":"102",
    "evaluationType":"CLOUD"
    }
``` 

#### Get all homework activities

```http
  GET /activity/homework
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of the item to fetch |


#### Get all wishes by child

```http
  GET /wish/${userId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of the item to fetch |


#### Get period by id

```http
  GET /period/${periodId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of the item to fetch |
## API Authentication and Authorization

There are only two requests which don't require authorization headers.

#### Authenticate (login)

```http
  POST /authenticate
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of user to authenticate  |

Request body example:

```json
{
  "password": "string",
  "username": "string"
}
```  

#### Register

```http
  POST /register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**. The properties of user to register  |

Request body example:

```json
{
  {
  "email": "string",
  "password": "string",
  "username": "string"
}
}
```  
After running the authenticate request, the client will obtain an access token that will be used in all subsequent request in order to authenticate the user and to authorize the user based on its role.

This is an example of what should be included in the request header:

```http
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjcxMTQzMzEyfQ.dxIzsD9Bm8y_kw3MOoZ2JXIKOg--uZaA5XNtBLdGYc4Ps3nlzBFDwBJi0bEeHlCggonZ6nQ2zwCI0D5a7dXjmw
```  


## Prerequisites
For building and running the application you need:
- JDK 1.8 or higher
- Maven 3

For deploying on Heroku you need:
- GIT
- Heroku CLI
  Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

## Dependencies
You don't need any additional dependencies.
All dependecies related to database management, server management, security management and so on, will be automatically injected by Maven using the pom.xml file located in the root folder of the project.

## Installation

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```


## Run Locally

Use maven to build the app and, to run it, and to start the local embedded Tomcat server

```bash
  mvn spring-boot:run
```



## Running Tests

To run tests, run the following command

```bash
  npm run test
```


## Environment Variables

You can deploy this project using Heroku or other providers as well, by specifying the following environment variables:

`PROFILE`

`MYSQL_URL`

`MYSQL_USER`

`MYSQL_PASS`



## Deployment
To deploy this project run

```bash
  git push heroku master
```



## Usage/Examples

You cand use the a demo version of the app, using SwaggerUI and following this link:

```javascript
https://obscure-peal.heroku.app/swagger-ui/
```

First, obtain an access token by running the /authenticate endpoint with username "user" and password "pass", which will grant you admin rights in the application.

![App Screenshot](https://i.imgur.com/VTQibfA_d.webp?maxwidth=760&fidelity=grand)

After that, authorize yourself by clicking the authorize button and copy-pasteing the token from the response.

![App Screenshot](https://i.imgur.com/arTX2Ke_d.webp?maxwidth=760&fidelity=grand)

From now on, you can use all other available endpoints, as per swagger documentation.




## Roadmap

In the future, application can be extended with following:

- Let the child add the wishes, and the parent approve them
- Let the child add activity to the activity type "homework"




## Badges
![Maintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![GIT](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)



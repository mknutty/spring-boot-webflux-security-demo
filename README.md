# Spring Boot, Webflux, Resource Server, Secret Key and JWT Security Example

Demonstrates using JWT with a Secret key to manage user identity and granted authorities.

Look at SecurityController for usage and more info.

To get a JWT, call the /jwt endpoint to get a JWT to pass as the bearer token to the /hello endpoint. 

The app starts on port 7001. Change that in application.properties as needed.
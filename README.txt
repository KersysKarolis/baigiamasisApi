ORDER API:
--API CREATED WITH SPRING-BOOT--
Project intended for order creation in your system.
API includes PostgreSQL database which has to be configured in application.properties file.
Database currently is configured by default values, before integration in your system
it is strongly advised to change login credentials.
API requests (CRUD operations) are authenticated and secured with basic authorization.

For project initialization default employee created with username: admin, password: admin.
Default employee will be designated as ADMIN which allows successfully manipulate with requests.
All requests can be paged and sorted by fields also create, update requests are validated by SpringBoot itself.
 
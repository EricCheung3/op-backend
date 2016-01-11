# op-backend
Openprice system backend code

## Local Development Environment Setup
Install [Java8 JDK 8u45 from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Install [Apache Maven 3.3.3](https://maven.apache.org/download.cgi)

Install Java IDE Eclipse from [Spring Tool Suite 3.7.0.RELEASE](http://spring.io/tools/sts/all)

Also download [Project Lombok](https://projectlombok.org/) and run the
lombok.jar to add it into Spring Tool Suite.

## Other tools
Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

Install [Docker](https://docs.docker.com/installation/)

Install [MySQL 5.6](https://dev.mysql.com/doc/refman/5.6/en/installing.html)

Install [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

## Github Account
Create free account in Github:
[https://github.com/](https://github.com/)

Ask admin to add your account to groundtruthinc team (opgt).

Setup [SSH key for Github](https://help.github.com/articles/generating-ssh-keys/)

Fork the project from [Open Price Backend Project](https://github.com/opgt/op-backend)
to your personal account through Web UI at Bitbucket.

See [Software Development Life Cycle document](https://bitbucket.org/groundtruthinc/openpriceproj/wiki/SDLC)
for detail process.

## Run OpenPrice Backend Project locally first time

### Setup local MySQL server
Start local MySQL Server, with a database schema *openpricelocal*, with user *openprice*, password *openprice*.

### Setup local image storage folder
Create a local image storage folder at `/tmp/groundtruth/images/`

This is configured at `openpriceproj/web-api/src/main/resources/config/application-local.yml`. You can change it to fit your local env.

### Checkout code
Check out the code from Github your private repository for **openpriceproj**.
When your change is done, do a pull request, and team lead will merge the
changes into dev branch.


### Run API Server Locally
Assume you already installed Java8, Maven, STS 3.7.1 locally.

#### Running inside Eclipse (STS)
* Import backend projects into Eclipse as existing Maven projects from op-backend folder.
* Select "Run -> Run Configurations..." in Eclipse.
* Create a Java Application, such as "OpenPrice Local Dev".
* In Main tab, set *Project* to "openprice-web-api", and *Main class* to "com.openprice.OpenPriceAPIApplication"
* In Arguments tab, add "--spring.profiles.active=dev,local,noemail" to *Program arguments*. Set *Working directory* to "${workspace_loc:openprice-web-api}"

Then you can run it inside Eclipse.

#### Running in command line
~~~
mvn clean package -Pdev
java -jar ./web-api/target/openprice-web-api.jar --spring.profiles.active=dev,local,noemail
~~~

## Run OpenPrice Backend Project with local Docker
Assume you have already installed Docker in your local machine. 


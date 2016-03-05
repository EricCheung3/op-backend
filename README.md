# op-backend
Openprice system backend code.

## Local Development Environment Setup
Install [Java8 JDK from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Install [Apache Maven 3.3.3](https://maven.apache.org/download.cgi)

Install Java IDE Eclipse from [Spring Tool Suite 3.7.2.RELEASE](http://spring.io/tools/sts/all)

Also download [Project Lombok](https://projectlombok.org/) and run the
lombok.jar to add it into Spring Tool Suite.

Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

Install [Docker](https://docs.docker.com/installation/)

Install [MySQL 5.6](https://dev.mysql.com/doc/refman/5.6/en/installing.html)

Install [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

### Github Account
Create free account in Github:
[https://github.com/](https://github.com/)

Ask admin to add your account to groundtruth (opgt).

Setup [SSH key for Github](https://help.github.com/articles/generating-ssh-keys/)

Fork the project from [Open Price Backend Project](https://github.com/opgt/op-backend)
to your personal account through Web UI at Github.

### Checkout code
Create a local folder for `~/groundtruth/git`, and check out the code from Github your private repository for **op-backend** to this folder.

Try to run `mvn clean install` inside `~/groundtruth/git/op-backend` to see if you can build the system locally.

When your change is done, do a pull request, and team lead will merge the
changes into release branch. See [Software Development Life Cycle document](https://bitbucket.org/groundtruthinc/openpriceproj/wiki/SDLC)
for detail process.


## Run Openprice Backend Project locally with Docker

### Setup local image storage folder
Create a local image storage folder at `~/groundtruth/images/`, and add subfolder `receipts`.

### Setup local log folder
Create a local log folder at `~/groundtruth/logs/`.

### Run Backend Servers inside Docker container
Assume you alreadt have Docker installed. If docker VM is not created for the project, run this script to create
`docker-machine create --virtualbox-disk-size "20000" --virtualbox-cpu-count "2" --virtualbox-memory "4096" -d virtualbox opgt`

Copy op-backend/scripts/local/* to your local folder, such as `~/groundtruth/local`, and make sure the bash script files are executable. Or run `chmod +x *`.

For first time in a Terminor window to run docker, setup the env by running
`eval "$(docker-machine env opgt)"`

Then run `./init-start` to build and run the servers in Docker.

# XMLCourseProject

TO INSTALL THE NECESSERY TOOLS GO TO THE HOME DIRECTORY OF YOUR UBUNTU OR DEBIAN DISTRIBUTON AND RUN THE FOLLOWING COMMANDS

```shell
mkdir tools
cd tools
curl -O https://dl.dropboxusercontent.com/s/v6c8st274vn77d1/marklogic_8.0-6.2_amd64.deb
sudo dpkg -i marklogic_8.0-6.2_amd64.deb
curl -O https://d1opms6zj7jotq.cloudfront.net/idea/ideaIU-2016.1.2.tar.gz
tar -xzf ideaIU-2016.1.2.tar.gz
sudo apt-get install openjdk-8-jdk
curl -O http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.zip
unzip wildfly-10.0.0.Final.zip
```

TO RUN THE MARKLOGIC SERVER RUN THE FOLLOWING COMMAND

```shell
sudo service MarkLogic start
```

TO RUN INTELLIJ IDEA GO TO THE EXTRACTED DIRECTORY/bin AND run ./idea.sh

TO RUN THE SERVER START INTELLIJ IDEA OPEN THE GITHUB PROJECT AND CONFIGURE THE JBOOS RUN CONFIGURATION
# Ncoding

1) Extract the zip file ncoding-1.0.0-SNAPSHOT.zip to ncoding-1.0.0-SNAPSHOT
2) Go to the folder ncoding-1.0.0-SNAPSHOT
3) GO to conf/application.conf and change your DB parameters
    db.default.url="jdbc:mysql://localhost/NCoding" -- Change localhost to your machine IP
    db.default.username=root -- Change to your mysql username
    db.default.password="root" -- Change password to your mysql user password, If no password then remove this line
4) GO to folder bin and run the command "ncoding"

This will start the server on port 9000, you can access the login page by calling localhost:9000 in your browser

How to load data onto your mysql server:

1) the data dump is available in the git repo 
   https://github.com/mandilshashank/Ncoding/blob/master/NCoding.sql
2) Download the dump file 
3) Upload dump by running the following command
    mysql -p -u <user> < {Path to Ncoding.sql}


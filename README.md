# GrizzlyJerseySecureHttpServer

This Sample project is about to create the secure Http server through Grizzly and Jersey.
Below are the command to create keystore and trusttore files.

keytool -genkey -keyalg RSA -keystore ./keystore_client -alias clientKey
keytool -export -alias clientKey -rfc -keystore ./keystore_client > ./client.cert
keytool -import -alias clientCert -file ./client.cert -keystore ./truststore_server

keytool -genkey -keyalg RSA -keystore ./keystore_server -alias serverKey
keytool -export -alias serverKey -rfc -keystore ./keystore_server > ./server.cert
keytool -import -alias serverCert -file ./server.cert -keystore ./truststore_client

In this, I have given the provision to read the port from Environment variable and properties file as well in case not found in env variable.


## Copyright 

Copyright (c) 2018 Tanuj Gupta

---

> GitHub [@guptakumartanuj](https://github.com/guptakumartanuj) &nbsp;&middot;&nbsp;
> [Blog](https://guptakumartanuj.wordpress.com/) &nbsp;&middot;&nbsp;

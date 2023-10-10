# bastian-fischer.dev-API

## Getting Started

* 1. Clone the Repository 

```
git clone https://github.com/elyps/bastian-fischer.dev-API.git
```
Use Git or checkout with SVN using the web URL.

* 2. Run Gradle Task `build`

* 3. Run Gradle Task `bootRun`


** IMPORTANT: You must have an *.env file and the correct paths and usernames, passwords set there.


## Encryption

You need a
`crypt.properties`
file to load the encryption key from the properties file and use it to encrypt and decrypt data.

** Syntax:
* Decryption
```
DEC(<decrypted_value>)
```

* Encryption
```
ENC(encrypted_value)
```

** All decrypted values in *env file are automatically encrypted at boot time.

* JasyptConfig.java
There you can set paths for the *.env file and the Propertysource (eg. crypt.properties)

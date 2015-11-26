# Login Web App

Just a simple login web app with integrated Java HttpServer. Funny having to deal with Cookies and Sessions.

- [Installation](#installation)
- [Tests](#tests)
- [Usage](#usage)
- [Authors](#authors)
- [License](#license)

## Installation

This project uses gradle. Just execute this command:
```shell
gradle build
```
You will get your package (.jar).

```shell
java -jar LoginWebApp-1.0.jar
```

Optionally, you can set the server port:

```shell
java -jar LoginWebApp-1.0.jar 9200
```

## Tests

Again, use gradle:
```shell
gradle test
```

## Usage

### Users
Available users:
```text
jimmykoak
adrian.robles.maiz
someone
awesome_user
root
```
Password for all users: 12345

## Authors

* [Adrián Robles Maiz (a.k.a Jimmy K. Oak)] (http://github.com/jimmyoak)

## License
The code base is licensed under the MIT license.
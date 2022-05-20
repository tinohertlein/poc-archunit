# poc-archunit

[![CI](https://github.com/tinohertlein/poc-archunit/actions/workflows/ci.yml/badge.svg)](https://github.com/tinohertlein/poc-archunit/actions/workflows/ci.yml)

This repository contains a proof of concept, how violations of a given architecture pattern can be recognized
with [ArchUnit](https://www.archunit.org/) included into a Maven-lifecycle.

In order to do this, a simple CRUD-application with a "user"-domain is implemented in Java with the architecture
pattern ["Ports & Adapters"](https://medium.com/idealo-tech-blog/hexagonal-ports-adapters-architecture-e3617bcf00a0).

## System Architecture

The `UserService` is available to the outside via an API (`UserApiAdapter` - a primary adapter) and stores/loads
entities via a persistence layer (`UserPersistenceAdapter` - a secondary adapter). The adapters and the `UserService`
are loosely coupled via port-interfaces, which are located in the `core` package.

![image](docs/system.png)

For simplicity reasons, neither API nor persistence are implemented using frameworks like Spring. They are only coded as
simple stubs instead.

## Tests

To show the inclusion of ArchUnit into the Maven build & testing lifecycle in conjunction with other types of tests, a
couple of integration tests and unit tests are realized as well, but they do not cover the whole functionality.

### ArchUnit

The following relations between classes & interfaces are checked with ArcUnit:

* Interfaces & classes in package `core`
    * **may only** depend on interfaces or classes inside their own package
* Interfaces & classes in package `adapter`
    * **may only** depend on interfaces or classes inside their own package
    * **may only** depend on interfaces inside their corresponding package in `core.port`

#### Disclaimer

There are certainly many more best-practices concerning dependencies among classes & interfaces, that are also worth to
be checked with ArchUnit, but only the afore-mentioned ones are realized in this proof of concept.



# Release Process

* code: https://github.com/watson-developer-cloud/spring-boot-starter
* maven: http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22watson-spring-boot-starter%22

## Guide to uploading artifacts to the Central Repository

We use Sonatype as repository manager, it's used as the input channel for the Central Repository running the Sonatype Open Source Repository Hosting(OSSRH) service.

### Prerequisites

If you are not familiar with Sonatype and/or the maven release process please read the following material:

* Releasing artifacts to Sonatype using Gradle: http://central.sonatype.org/pages/gradle.html
* Install GPG, and create a public key. More info: http://central.sonatype.org/pages/working-with-pgp-signatures.html

### Release steps

* Commits should follow the [Angular commit message guidelines](https://github.com/angular/angular/blob/master/CONTRIBUTING.md#-commit-message-guidelines). This is because our release tool uses this format for determining release versions and generating changelogs. To make this easier, we recommend using the [Commitizen CLI](https://github.com/commitizen/cz-cli) with the `cz-conventional-changelog` adapter.

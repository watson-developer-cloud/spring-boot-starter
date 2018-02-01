# Release Process

* code: https://github.com/watson-developer-cloud/spring-boot-starter
* maven: http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22spring-boot-starter%22

## Guide to uploading artifacts to the Central Repository

We use Sonatype as repository manager, it's used as the input channel for the Central Repository running the Sonatype Open Source Repository Hosting(OSSRH) service.

### Prerequisites

If you are not familiar with Sonatype and/or the maven release process please read the following material:

* Releasing artifacts to Sonatype using Gradle: http://central.sonatype.org/pages/gradle.html
* Install GPG, and create a public key. More info: http://central.sonatype.org/pages/working-with-pgp-signatures.html

### Release steps

  1. Update all READMEs to include the new version number

     This can be done using [bumpversion]. If necessary, it can be installed with the following command:

     ```bash
     pip install bumpversion
     ```

     To then update all version numbers, simply run:

     ```bash
     bumpversion major|minor|patch
     ```

  1. Perform a release deployment to OSSRH (Staging) with:

     ```bash
     gradle release
     ```

     You will have to answer prompts for versions and tags. That will tag and commit a new version into your repository automatically.


### Travis setup

Travis should have the following environment variables setup:
 * `CI_DEPLOY_USERNAME`: JIRA username with write permission
 * `CI_DEPLOY_PASSWORD`: JIRA password for the username above
 * `SIGNING_KEY`: PGP public key id
 * `SIGNING_PASSWORD`: PGP public key password
 * `SIGNING_KEYRING_PATH`: Relative path to the public key
 * `GITHUB_TOKEN`: Github token
[bumpversion]: https://pypi.python.org/pypi/bumpversion
#!/usr/bin/env bash

gpg --quiet --batch --yes --decrypt --passphrase="$SIGNING_PASSPHRASE" --output ./scripts/signing.key ./scripts/signing.key.gpg
./gradlew uploadArchives -Psigning.keyId=$SIGNING_KEY -Psigning.password=$SIGNING_PASSWORD -Psigning.secretKeyRingFile=./scripts/signing.key
#./gradlew closeAndReleaseRepository -PnexusUsername=$CI_DEPLOY_USERNAME -PnexusPassword=$CI_DEPLOY_PASSWORD

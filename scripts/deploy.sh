#!/usr/bin/env bash

openssl aes-256-cbc -K $encrypted_c32defe8b915_key -iv $encrypted_c32defe8b915_iv -in scripts/secring.gpg.enc -out scripts/secring.gpg -d
./gradlew uploadArchives -Psigning.keyId=$SIGNING_KEY -Psigning.password=$SIGNING_PASSWORD -Psigning.secretKeyRingFile=./scripts/secring.gpg
./gradlew closeAndReleaseRepository -PnexusUsername=$CI_DEPLOY_USERNAME -PnexusPassword=$CI_DEPLOY_PASSWORD

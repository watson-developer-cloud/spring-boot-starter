#!/usr/bin/env bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
  openssl aes-256-cbc -K $encrypted_c32defe8b915_key -iv $encrypted_c32defe8b915_iv -in secring.gpg.enc -out scripts/secring.gpg -d
  ./gradlew uploadArchives -Psigning.keyId=$SIGNING_KEY -Psigning.password=$SIGNING_PASSWORD -Psigning.secretKeyRingFile=./scripts/secring.gpg
  ./gradlew closeAndReleaseRepository
fi

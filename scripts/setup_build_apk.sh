#!/bin/bash
set -e

if [ "$(echo "$BUILD_ENV" | tr '[:upper:]' '[:lower:]')" == "prod" ]; then
  ./gradlew clean assemblePlayStoreRelease
else
  ./gradlew clean assemblePlayStoreDev
fi
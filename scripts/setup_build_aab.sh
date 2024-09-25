#!/bin/bash
set -e

if [ "$(echo "$BUILD_ENV" | tr '[:upper:]' '[:lower:]')" == "production" ]; then
  ./gradlew clean bundlePlayStoreRelease
else
  ./gradlew clean bundlePlayStoreDev
fi
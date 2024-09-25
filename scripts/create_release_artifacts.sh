#!/bin/bash
set -e

if [ "$(echo "$BUILD_ENV" | tr '[:upper:]' '[:lower:]')" == "production" ]; then
  jobPlanet_android/build/outputs/bundle/korRelease/*.aab
  jobPlanet_android/build/outputs/apk/kor/release/*.apk
elif [ "$(echo "$BUILD_ENV" | tr '[:upper:]' '[:lower:]')" == "qa" ]; then
  jobPlanet_android/build/outputs/bundle/korRc/*.aab
  jobPlanet_android/build/outputs/apk/kor/rc/*.apk
else
  jobPlanet_android/build/outputs/bundle/korDebug/*.aab
  jobPlanet_android/build/outputs/apk/kor/debug/*.apk
fi
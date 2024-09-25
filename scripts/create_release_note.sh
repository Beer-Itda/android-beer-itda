#!/bin/bash
set -e

#- name: Find commits from last VERSION_NAME change to HEAD
if [ "$BUILD_ENV" == "PRODUCTION" ]; then
  ./gradlew clean bundleKorRelease
  jarsigner -verify -verbose -certs jobPlanet_android/build/outputs/bundle/korRelease/jobPlanet_android-kor-release.aab
elif [ "$BUILD_ENV" == "QA" ]; then
  ./gradlew clean bundleKorRc
  jarsigner -verify -verbose -certs jobPlanet_android/build/outputs/bundle/korRc/jobPlanet_android-kor-rc.aab
else
  ./gradlew clean bundleKorDebug
  jarsigner -verify -verbose -certs jobPlanet_android/build/outputs/bundle/korDebug/jobPlanet_android-kor-debug.aab
fi

#- name: Extract changelog from commits since the last VERSION change

if [ -z "${{ env.LAST_VERSION_COMMIT }}" ]; then
    echo "No previous VERSION change found, extracting all commits."
    CHANGELOG=$(git log --pretty=format:"%h - %s" --abbrev-commit)
  else
    echo "Last VERSION change found at commit ${{ env.LAST_VERSION_COMMIT }}. Extracting commits since this change."
    CHANGELOG=$(git log ${{ env.LAST_VERSION_COMMIT }}..HEAD --pretty=format:"%s" --abbrev-commit)
  fi
  echo "CHANGELOG<<EOF" >> $GITHUB_ENV
  echo "$CHANGELOG" >> $GITHUB_ENV
  echo "EOF" >> $GITHUB_ENV


echo "${{ env.CHANGELOG }}" >> release_notes.txt
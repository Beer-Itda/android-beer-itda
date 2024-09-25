#!/bin/bash
set -e

chmod +x gradlew
chmod +x ./tools/auto_increase_version_code_for_linux.sh
./tools/auto_increase_version_code_for_linux.sh

echo "$GOOGLE_SERVICES_JSON" | base64 --decode > jobPlanet_android/google-services.json
echo "$KEYSTORE_PROPERTIES" | base64 --decode > keystore.properties
echo "$KEYSTORE_FILE" | base64 --decode > jobPlanet_android/$KEYSTORE_NAME
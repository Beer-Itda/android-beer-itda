#!/bin/bash
SCRIPT_PATH=`dirname $0`
XML_ATTR="VERSION_NAME"
FILE="$SCRIPT_PATH/../buildSrc/src/main/kotlin/AndroidConfig.kt"

echo "newVersion: $RELEASE_VERSION"

sed -i "s/${XML_ATTR}=\".*\"/${XML_ATTR}=\"${$RELEASE_VERSION}\"/g" $FILE
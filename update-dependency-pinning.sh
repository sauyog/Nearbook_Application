#!/bin/bash
set -e

PROJECTS=(
    'nearbook-api'
    'nearbook-core'
    'nearbook-android'
    'nearbook-java'
    'masterproject-api'
    'masterproject-core'
    'masterproject-android'
    'nearbook-headless'
)

# clear witness files to prevent errors when upgrading dependencies
for project in ${PROJECTS[@]}
do
    echo "" > ${project}/witness.gradle
done

# calculating new checksums
for project in ${PROJECTS[@]}
do
    echo "Calculating new checksums for ${project} ..."
    ./gradlew -q --configure-on-demand ${project}:calculateChecksums | grep -v '^\(Skipping\|Verifying\|Welcome to Gradle\)' > ${project}/witness.gradle
done

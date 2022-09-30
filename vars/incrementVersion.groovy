#!/usr/bin/env groovy

def call() {
    echo 'Incrementing app version'
    sh "mvn build-helper:parse-version versions:set \
    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
    versions:commit"
    def matchedFile = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matchedFile[0][1] // text inside <version>
    env.VERSION = version
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}

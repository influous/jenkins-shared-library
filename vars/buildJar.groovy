#!/usr/bin/env groovy

def call() {
    echo "Building the application (JAR) for branch v${env.BRANCH_NAME}..."
    sh 'mvn package'
}

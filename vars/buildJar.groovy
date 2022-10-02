#!/usr/bin/env groovy

def call() {
    echo "Building the application v${env.IMAGE_TAG}..."
    sh 'mvn clean package'
}

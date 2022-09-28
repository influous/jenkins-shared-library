#!/usr/bin/env groovy

def call() {
    echo 'Building Docker image'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
    sh 'docker build -t influous/infx-repo:dm1.1 .'
    sh "echo '${PASSWORD}' | docker login -u $USER --password-stdin"
    sh 'docker push influous/infx-repo:dm1.1'
    }
}
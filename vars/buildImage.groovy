#!/usr/bin/env groovy

def call(String imageName) {
    echo 'Building Docker image'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
    sh "docker build -t $imageName . "
    sh "echo '${PASSWORD}' | docker login -u $USER --password-stdin"
    sh "docker push $imageName"
    }
}
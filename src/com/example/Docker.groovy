#!/usr/bin/env groovy
package com.example

class Docker implements Serializable {

    def script

    Docker(script) {
        this.script = script
    }

    def buildImage() {
        script.echo 'Building Docker image...'
        if(script.BRANCH_NAME == "feature/deploy-on-aws-ecr") {
            script.sh "docker build -t ${script.DOCKER_REPO}:${script.BUILD_TAG} -t ${script.DOCKER_REPO}:${script.BUILD_LATEST} ."
        } else {
            script.sh "docker build -t ${script.IMAGE_BUILD} -t ${script.IMAGE_LATEST} ."
        }
    }

    def dockerLogin() {
        if(script.BRANCH_NAME == "feature/deploy-on-aws-ecr") {
            script.withCredentials([script.usernamePassword(credentialsId: 'ecr-credentials', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                script.sh "echo '${script.PASSWORD}' | docker login -u ${script.USER} --password-stdin ${script.DOCKER_REPO_SERVER}"
            }
        } else {
            script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) {
                script.sh "echo '${script.PASSWORD}' | docker login -u ${script.USER} --password-stdin"
            }
        } 
    }

    def dockerPush() {
        if(script.BRANCH_NAME == "feature/deploy-on-aws-ecr") {
            script.sh "docker push ${script.DOCKER_REPO}:${script.BUILD_TAG}"
            script.sh "docker push ${script.DOCKER_REPO}:${script.BUILD_LATEST}"
            script.sh "docker rmi ${script.DOCKER_REPO}:${script.BUILD_LATEST}"
        } else {
            script.sh "docker push ${script.IMAGE_BUILD}"
            script.sh "docker push ${script.IMAGE_LATEST}"
            script.sh "docker rmi ${script.IMAGE_BUILD}"
        }
    }
    
}
#!/usr/bin/env groovy

def call() {
    echo "Deploying to branch ${env.BRANCH_NAME}"
    def dockerCmd = "docker run -d -p 8080:8080 ${IMAGE_NAME}"
    sshagent(['ec2-ssh-key']) {
    // -o flag avoids SSH popup
    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_ADDRESS} ${dockerCmd}"
    }
}

#!/usr/bin/env groovy

def call() {
    echo "Deploying to branch ${env.BRANCH_NAME}"
    def dockerCmd = "docker-compose -f docker-compose.yaml up --detach"
    sshagent(['ec2-ssh-key']) {
    sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    // -o flag avoids SSH popup
    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_ADDRESS} ${dockerCmd}"
    }
}

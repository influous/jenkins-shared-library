#!/usr/bin/env groovy

def call() {
    echo "Deploying to branch ${env.BRANCH_NAME}"
    def shellCmds = "bash ./server-cmds.sh"
    sshagent(['ec2-ssh-key']) {
    // -o flag avoids SSH popup
    sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_ADDRESS} ${shellCmds}"
    }
}

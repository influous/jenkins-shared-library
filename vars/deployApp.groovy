#!/usr/bin/env groovy

def call() {
    echo "Deploying to EC2 instance on ${env.EC2_ADDRESS}"
    def shellCmds = "bash ./server_cmds.sh ${env.IMAGE_LATEST}"
    sshagent(['ec2-ssh-key']) {
    // -o flag avoids SSH popup
    sh "scp -o StrictHostKeyChecking=no server_cmds.sh ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${EC2_USER}@${EC2_ADDRESS}:/home/ubuntu"
    sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_ADDRESS} ${shellCmds}"
    }

    if(env.BRANCH_NAME == 'feature/deploy-on-k8s') {
        try {
            sh "kubectl create deployment nginx-deployment --image=nginx"
        } catch (Exception e) {
            echo "Exception: " + e.toString()
        }
    }
}

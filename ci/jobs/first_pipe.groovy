#!groovy
@Library(value='jenkins@master', changelog=false) _
import com.test.ci.Commands

def cmds = new Commands()
def buildStatus = "FAILED"
def HOME = "/var/jenkins_home"

pipeline {
    agent any
    parameters {
        string(name: 'PROJECT_BRANCH', defaultValue: 'master', description: "infrastructure project")
        string(name: 'CI_BRANCH', defaultValue: 'master', description: 'repo for jenkins jobs')
    }
    environment {
        repository = "git@github.com:akhmetgali/infra_demo.git"
        gitCredentialId = 'akhmetgali'

    }
    stages {
        stage('Prepare') {
            steps {
                script {
                    cmds.checkoutSource("${gitCredentialId}", "${repository}", "master")
                }
            }
        }
        stage('Install ruby') {
            steps {
                script {
                    cmds.installRuby("${HOME}")
                }
            }
        }
    }
}

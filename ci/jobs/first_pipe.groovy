#!groovy
@Library(value='jenkins@master', changelog=false) _
import com.test.ci.Commands

def cmds = new Commands()
def buildStatus = "FAILED"
def HOME = "/var/jenkins_home"

pipeline {
    agent { node { label "spot"} }
    parameters {
        string(name: 'PROJECT_BRANCH', defaultValue: 'master', description: "infrastructure project")
        string(name: 'CI_BRANCH', defaultValue: 'master', description: 'repo for jenkins jobs')
    }
    environment {
        repository = "git@github.com:akhmetgali/infra_demo.git"
        gitCredentialId = 'akhmetgali'

    }
    stages {
        // stage('Connect to VM') {

        // }
        stage('Checkout Repo into VM') {
            steps {
                script {
                    cmds.checkoutSource("${gitCredentialId}", "${repository}", "master")
                }
            }
        }
        stage('Install ruby') {
            steps {
                script {
                    cmds.installRuby("${repository}", "master")
                }
            }
        }
        // stage('Install Mongo') {

        // }
        // stage('Deploy app') {

        // }
    }
}

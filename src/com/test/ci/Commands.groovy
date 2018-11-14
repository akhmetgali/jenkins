package com.test.ci

def checkoutSource(String gitCredentialId, String repositoryUrl, String branch, String relativeTargetDir = "") {
    if (repositoryUrl == 'git@github.com:akhmetgali/jenkins.git') {
        showChangelog = false
    }
    else {
        showChangelog = true
    }

    def gitStatus = checkout([
        changelog: showChangelog,
        scm     : [
                $class      : 'GitSCM',
                branches    : [[name: "${branch ?: 'master'}"]],
                extensions  : [
                    [$class: 'CloneOption', shallow: false, depth: 1],
                    [$class: 'RelativeTargetDirectory', relativeTargetDir: relativeTargetDir ?: ""]
                ],
                userRemoteConfigs: [
                    [credentialsId: gitCredentialId, url: repositoryUrl]
                ]
        ]
    ])
    
    return gitStatus
}
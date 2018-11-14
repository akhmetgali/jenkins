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

def installRuby(String jenkinsHome, String repositoryUrl, String branch) {
    def cloneRepo = checkout([
        scm     : [
            $class      : 'GitSCM',
            branches    : [[name: "${branch ?: 'master'}"]],
            extensions  : [
                    [$class: 'CloneOption', shallow: false, depth: 1]
            ],
            userRemoteConfigs: [
                    [credentialsId: gitCredentialId, url: repositoryUrl]
            ]
        ]
    ])
    sh script: "ls -la"
    // sh script: "chmod +x ${jenkinsHome}/infra_demo/scripts/install_ruby.sh"
    // sh script: ".${jenkinsHome}/infra_demo/scripts/install_ruby.sh"
    return this
}
#!groovy

properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '15')), pipelineTriggers([])])

def runGradle(command) {
  if (isUnix()) {
    sh "./gradlew -PDashOHome='${env.DashOHome}' $command"
  } else {
    bat "gradlew.bat -PDashOHome='${env.DashOHome}' $command"
  }
}

node('android') {

  stage ('Checkout') {
    checkout scm
  }
  stage ('Clean') {
    runGradle("clean")
  }
  stage ('Build') {
    runGradle("build")
  }
}

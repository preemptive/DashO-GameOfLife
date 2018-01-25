#!groovy

properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '15')), pipelineTriggers([])])

def runGradle(command) {
  if (isUnix()) {
    sh "./gradlew $command"
  } else {
    bat "gradlew.bat $command"
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

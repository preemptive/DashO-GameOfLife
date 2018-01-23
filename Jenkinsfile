#!groovy

properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '15')), pipelineTriggers([])])

node('android') {
  stage ('Checkout') {
    checkout scm
  }
  stage ('Clean') {
    bat 'gradlew.bat clean'
  }
  stage ('Build') {
    bat 'gradlew.bat build'
  }
}

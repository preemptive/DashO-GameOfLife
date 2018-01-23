#!groovy

properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '15')), pipelineTriggers([])])

node('gradle_18') {
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

pipeline {
  agent any
  options {
    timestamps()
    ansiColor('xterm')
  }
  tools {
    maven 'maven'
  }
  environment {
    JAVA_HOME = tool name: 'jdk17', type: 'jdk'
    PATH = "${JAVA_HOME}/bin:${env.PATH}"
  }
  stages {
    stage('Setup') {
      steps {
        sh 'java -version'
        sh 'mvn -v'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn clean install'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml', allowEmptyArchive: true
        }
      }
    }
  }
  post {
    success {
      echo 'Build & tests succeeded.'
    }
    failure {
      echo 'Build failed or tests failed.'
    }
  }
}


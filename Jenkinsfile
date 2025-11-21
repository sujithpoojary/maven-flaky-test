pipeline {
  agent any
  options {
    timestamps()
    ansiColor('xterm')
  }
  tools {
    maven 'maven'
  }
  parameters {
    // Optional selector: run only certain tests
    string(name: 'test', defaultValue: '', description: 'Optional Surefire test selector (e.g. MyTest#method)')
    // Number of deflake attempts (how many times Jenkins reruns on failure)
    string(name: 'RETRY_COUNT', defaultValue: '2', description: 'Number of times to retry failed tests')
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

    stage('Test (Surefire Deflake)') {
      steps {
        script {
          def attempts = params.RETRY_COUNT.toInteger()
          def success = false
          def testSelector = params.test?.trim()
          def testFlag = testSelector ? "-Dtest='${testSelector}'" : ""

          for (int i = 1; i <= attempts + 1; i++) {
            echo "🧪 Running test attempt #${i}"

            // Run Surefire tests
            def result = sh(
              script: "mvn -B clean test ${testFlag}",
              returnStatus: true
            )

            // Publish test results for each run
            junit testResults: 'target/surefire-reports/*.xml',
                  allowEmptyResults: true,
                  testDataPublishers: [junitFlakyTestPublisher()]

            if (result == 0) {
              echo "✅ Tests passed on attempt #${i}"
              success = true
              break
            } else {
              echo "⚠️ Attempt #${i} failed"
            }
          }

          if (!success) {
            error("❌ Tests failed after ${attempts + 1} attempts.")
          }
        }
      }
      post {
        always {
          archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml', allowEmptyArchive: true
        }
      }
    }
  }

  post {
    success { echo 'Build & tests succeeded (Deflake handled any flaky tests).' }
    failure { echo 'Build failed or tests still failed after retries.' }
  }
}

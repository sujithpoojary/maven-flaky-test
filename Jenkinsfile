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

    stage('Test (Surefire Deflake: selective)') {
    steps {
      script {
        int maxRetries = (params.RETRY_COUNT ?: '2') as int
        String baseFlags = params.test?.trim() ? "-Dtest='${params.test.trim()}'" : ""
        boolean passed = false
  
        // 1) Initial full run
        echo "🧪 Attempt #1: full suite"
        int rc = sh(script: "mvn -B clean test ${baseFlags}", returnStatus: true)
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true, testDataPublishers: [junitFlakyTestPublisher()]
        if (rc == 0) { passed = true }
  
        // helper to collect failed tests as Class#method selectors
        def collectFailedSelectors = {
          def selectors = []
          // Read all TEST-*.xml and extract failed/error testcases
          def reportFiles = sh(script: "ls target/surefire-reports/TEST-*.xml 2>/dev/null || true", returnStdout: true).trim().split("\\s+").findAll { it }
          for (f in reportFiles) {
            def xml = readFile(file: f)
            def root = new XmlSlurper().parseText(xml)
            root.'**'.findAll { it.name() == 'testcase' }.each { tc ->
              def hasFailure = (tc.failure.size() > 0) || (tc.error.size() > 0)
              if (hasFailure) {
                String cls = (tc.@classname as String)
                String mtd = (tc.@name as String)
                if (cls && mtd) selectors << "${cls}#${mtd}"
              }
            }
          }
          // Surefire supports comma-separated selectors; also collapse per-class when many failures:
          selectors = selectors.unique()
          // If too many methods in one class, you can compress to ClassName (run all methods in that class)
          return selectors
        }
  
        // 2) Selective reruns
        int attempt = 2
        while (!passed && attempt <= maxRetries + 1) {
          def failedSelectors = collectFailedSelectors()
          if (!failedSelectors || failedSelectors.isEmpty()) {
            echo "✅ No failed tests found to rerun."
            passed = true
            break
          }
  
          // Build -Dtest argument: e.g. ClassA#method1,ClassB#method2
          // Use quoting to avoid shell expansion
          String selectorArg = "-Dtest='" + failedSelectors.join(",") + "'"
          echo "🧪 Attempt #${attempt}: rerunning failed tests only (${failedSelectors.size()} selectors)"
          // IMPORTANT: no 'clean' here — we only rerun selected tests
          rc = sh(script: "mvn -B test ${selectorArg}", returnStatus: true)
          junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true, testDataPublishers: [junitFlakyTestPublisher()]
          if (rc == 0) {
            passed = true
            break
          }
          attempt++
        }
  
        if (!passed) {
          error("❌ Tests failed after ${maxRetries + 1} attempts (including selective reruns).")
        }
      }
    }
    post {
      always {
        archiveArtifacts artifacts: 'target/cucumber/**/*.json, target/cucumber/**/*.xml', allowEmptyArchive: true
      }
    }

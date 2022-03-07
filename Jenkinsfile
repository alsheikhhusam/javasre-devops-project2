pipeline {
  agent any
  options {
    // This is required if you want to clean before build
    skipDefaultCheckout(true)
  }
  stages {
    stage('Clean') {
      steps {
          // Clean before build
          cleanWs()
          // We need to explicitly checkout from SCM here
          checkout scm
          echo "Building ${env.JOB_NAME}..."
      }
    }
  }
}
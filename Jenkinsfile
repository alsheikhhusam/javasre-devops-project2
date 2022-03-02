pipeline {
  agent any
  stages {
    stage('Test') {
    
      steps {
        dir('API1/WeatherAPI') {
          withMaven {
            sh 'mvn clean package -DskipTests'
          }
            }
            dir('API2/twilio-api') {
              withMaven {
                  sh 'mvn clean package -DskipTests'
              }
            }
            dir('API3/QueryAPI') {
              withMaven {
                  sh 'mvn clean package -DskipTests'
              }
            }
      }
    }
    stage('Build') {
      when {
        branch 'dev'
        branch 'main'
      }
        steps {
          sh "ls $WORKSPACE"
          dir('API1/WeatherAPI') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
          dir('API2/twilio-api') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
          dir('API3/QueryAPI') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
        }
    }

  }
}

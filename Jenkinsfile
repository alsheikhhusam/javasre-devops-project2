pipeline {
  agent any
  stages {
    stage('Test') {
    when  { anyOf {
      branch 'feature/*'
      branch 'dev'
      }
    }
      steps {
        dir('API1/WeatherAPI') {
          withMaven {
            sh 'mvn test'
          }
            }
            dir('API2/Twilio-api') {
              withMaven {
                  sh 'mvn test'
              }
            }
            dir('API3/QueryAPI') {
              withMaven {
                  sh 'mvn test'
              }
            }
      }
    }
    stage('Build') {
      when { anyOf {
        branch 'dev'
        branch 'main'
        }
      }
        steps {
          sh "ls $WORKSPACE"
          dir('API1/WeatherAPI') {
            withMaven {
              sh 'mvn clean package -DskipTests'
            }
          }
          dir('API2/Twilio-api') {
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
    stage('Docker Build') {
        when {
            branch 'dev'
        }
        steps {
            script {
                echo "$registry:$currentBuild.number"
                dockerImage = docker.build "$registry"
            }

        }
    }
  }
}

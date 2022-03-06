pipeline {
  environment {
    api1Registry = 'husamalsheikh/weatherapi'
    api2Registry = 'husamalsheikh/twilioapi'
    api3Registry = 'husamalsheikh/queryapi'
    dockerHubCreds = 'docker_hub'
    dockerImage = ''
  }
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
    stage('Maven Package') {
      when { anyOf {
        branch 'feature/*'
        branch 'dev'
        branch 'main'
        }
      }
        steps {
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
        when { anyOf {
          branch 'main'
        }
      }
        steps {
          dir('API1/WeatherAPI') {
            script {
                echo "$api1Registry:$currentBuild.number"
                dockerImage = docker.build "$api1Registry"
            }
          }
          dir('API2/Twilio-api') {
            script {
                echo "$api2Registry:$currentBuild.number"
                dockerImage = docker.build "$api2Registry"
            }
          }
          dir('API3/QueryAPI') {
            script {
                echo "$api3Registry:$currentBuild.number"
                dockerImage = docker.build "$api3Registry"
            }
          }
        }
    }
    stage('Docker Push') {
      when {
        branch 'main'
      }
      steps {
        dir('API1/WeatherAPI') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage.push("$currentBuild.number")
              dockerImage.push("latest")
            }
          }
        }
        dir('API2/Twilio-api') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage.push("$currentBuild.number")
              dockerImage.push("latest")
            }
          }
        }
        dir('API3/QueryAPI') {
          script {
            docker.withRegistry('', dockerHubCreds) {
              dockerImage.push("$currentBuild.number")
              dockerImage.push("latest")
            }
          }
        }
      }
    }
    stage('Wait for SRE approval') {
      when {
        branch 'main'
      }
      steps {
        script {
          try {
            timeout(time: 12, unit: 'HOURS') {
              approved = input message: 'Deploy to production?', ok: 'Continue',
              parameters: [choice(name: 'Approved', choices: 'Yes\nNo', description: 'Deploy build to production')]
              if(approved != 'Yes') {
                error('Build did not pass approval')
              }
            }
          } catch(error) {
            error('Build failed because timout was exceeded.')
          }
        }
      }
    }
  }
}
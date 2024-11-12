pipeline {
    agent any
    stages {
    stage('git repo & clean') {
    steps {
        //bat "rmdir /s /q shopping_cart"
        bat "git clone https://github.com/mahesht11/shopping_cart.git"
        bat "mvn clean -f shopping_cart"
        }
    }
    stage('install'){
        steps {
        bat "mvn install -f shopping_cart"
        }
    }
    stage('test'){
        steps{
        bat "mvn test -f shopping_cart"
        }
    }
    stage('Sonarqube') {
        steps {
            withSonarQubeEnv('sonarqube') {
            bat "mvn clean package sonar:sonar"
            }
        }
        }
    stage('package'){
    steps{
        bat "mvn package -f shopping_cart"
        }
    }

 }

}

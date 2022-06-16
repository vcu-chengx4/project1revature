node{
    def mvnHome = tool "MyMaven"
    def dockerImageTag= "dogistan/project2:${env.BUILD_NUMBER}"


    stage('clone repo'){
        git 'https://github.com/vcu-chengx4/project1revature'
        mvnHome = tool 'MyMaven'
    }


    stage ('Build demoservice project'){
        bat 'mvn clean install'

    }

     stage('Build docker image'){
        dockerImage = docker.build("dogistan/project2:${env.BUILD_NUMBER}")
    }

     stage('Build docker deploy'){
        echo "Docker Image Tag name : ${dockerImageTag}"
        docker.withRegistry('https://registry.hub.docker.com','docker_hub_credentials')
        {
        dockerImage.push("${env.BUILD_NUMBER}")
        dockerImage.push('latest')
        }
    }

    stage('Deploy to Kubernetes'){
        kubernetesDeploy(configs: "deplymentService.yml", kubeconfigId: "kubernetes")
    }

   
}
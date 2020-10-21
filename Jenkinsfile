pipeline {
    agent any
     tools {
       maven 'maven'
     }
     environment {
        NEXUS_URL = "http://13.212.71.76:8081/content/repositories/releases/"
	NEXUS_REPO_ID = "releases"
	GROUP_ID="`echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:groupId/text()' | xmllint --shell pom.xml | grep -v /`"
        ARTIFACT_ID="`echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:artifactId/text()' | xmllint --shell pom.xml | grep -v /`"
      	VERSION="`echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:version/text()' | xmllint --shell pom.xml | grep -v /`"
	NEXUS_VERSION="`echo -e 'setns x=http://maven.apache.org/POM/4.0.0\ncat /x:project/x:version/text()' | xmllint --shell pom.xml | grep -v /`.${BUILD_NUMBER}"
        FILE_NAME="target/${ARTIFACT_ID}-${VERSION}.war"
      }
      stages {
	 stage('Unit Test') {
            steps {
		tool name: 'maven', type: 'maven'
                sh 'mvn clean test'
	    }
	 }
	 stage("SonarQube analysis") {
             steps {
                 withSonarQubeEnv('sonar') {
                  sh 'mvn sonar:sonar'
                 }    
             }
         }
	 stage('package') {
            steps {
		tool name: 'maven', type: 'maven'
                sh 'mvn package'
		}
	 }
	 stage('Publish Artifacts') {
            steps {
              script{
	        sh "mvn -B deploy:deploy-file -Durl=$NEXUS_URL -DrepositoryId=$NEXUS_REPO_ID -DgroupId=$GROUP_ID -Dversion=$NEXUS_VERSION -DartifactId=$ARTIFACT_ID -Dpackaging=war -Dfile=$FILE_NAME"
	      }
            }
	  }
	  stage("Deploy to DEV environment") {
            steps {
		sshPublisher(publishers: [sshPublisherDesc(configName: 'devTomcat', 
							   transfers: [sshTransfer(cleanRemote: false, 
							   excludes: '', execCommand: '', 
							   execTimeout: 120000, 
							   flatten: false, 
							   makeEmptyDirs: false, 
							  noDefaultExcludes: false, 
							  patternSeparator: '[, ]+', 
							  remoteDirectory: '', 
							  remoteDirectorySDF: false, 
							  removePrefix: 'target', 
			          			  sourceFiles: 'target/*.war')], 
                             				  usePromotionTimestamp: false, 
							  useWorkspaceInPromotion: false, 
							  verbose: false)])
	    }
	 }
	 stage("Deploy to SIT Environment") {
            steps {
		sshPublisher(publishers: [sshPublisherDesc(configName: 'sitTomcat', 
							   transfers: [sshTransfer(cleanRemote: false, 
							   excludes: '', execCommand: '', 
							   execTimeout: 120000, 
							   flatten: false, 
							   makeEmptyDirs: false, 
							  noDefaultExcludes: false, 
							  patternSeparator: '[, ]+', 
							  remoteDirectory: '', 
							  remoteDirectorySDF: false, 
							  removePrefix: 'target', 
			          			  sourceFiles: 'target/*.war')], 
                             				  usePromotionTimestamp: false, 
							  useWorkspaceInPromotion: false, 
							  verbose: false)])
	    }
	  }
	      
	      
         /* stage('deploy') {
            steps {
                sh 'cp /var/lib/jenkins/workspace/Hello-world/target/java-tomcat-maven-example.war /opt/tomcat-8.5/webapps/'
		}
	      }
	  */
		/*stage("Quality Gate status") {
	  steps {
	    script {
           def qualitygate = waitForQualityGate()
                if (qualitygate.status != "OK") {
                  error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"	
		}
	    }
	    }
	}
	*/
	  /*stage("Quality Gate status") {
            steps {
              timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
              }
            }
          }
	  
	  */
	 
            }
}

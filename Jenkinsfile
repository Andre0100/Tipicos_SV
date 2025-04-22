pipeline {
    agent any
    tools{
        jdk 'jdk-21'
    }

    environment {
        // Carga las variables de entorno desde setenv.sh
        SETENV_PATH = "${WORKSPACE}/setenv.sh"
    }

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Cargando variables de entorno...'
                sh '. $SETENV_PATH'
                sh 'env | sort' // Opcional: para ver las variables cargadas en los logs
            }
        }

        stage('Compilar') {
            steps {
                echo 'Compilando proyecto...'
                sh 'mvn clean compile'
            }
        }

        stage('Empaquetar') {
            steps {
                echo 'Empaquetando proyecto...'
                sh 'mvn package'  // Empaqueta el proyecto WAR
            }
        }

        stage('Pruebas Unitarias') {
            steps {
                echo 'Ejecutando pruebas unitarias...'
                sh 'mvn test'
            }
        }

        stage('Cobertura') {
            steps {
                echo 'Generando reporte de cobertura...'
                sh 'mvn jacoco:report'
            }
        }

        stage('Pruebas de Integración') {
            steps {
                echo 'Ejecutando pruebas de integración...'
                sh '. $SETENV_PATH'  // Carga las variables de entorno
                sh 'mvn verify -Pintegration-tests -Dtest=IntegrationTestSuite'
            }
        }

        stage('Reporte Final') {
            steps {
                echo 'Finalizando pipeline'
                // Aquí puedes copiar o publicar resultados si quieres
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            //junit '**/target/failsafe-reports/*.xml'
            echo 'Pipeline finalizado.'
        }
        failure {
            echo '❌ Algo falló durante la ejecución del pipeline.'
        }
    }
}

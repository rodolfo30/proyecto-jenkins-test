pipeline {
    agent any

    stages {
        stage('Fase 1: Checkout') {
            steps {
                echo 'Descargando el código fuente desde GitHub...'
            }
        }
        
        stage('Fase 2: Compilación / Build') {
            steps {
                echo 'Compilando el proyecto y preparando dependencias...'
                sh 'echo "Proyecto compilado con éxito."'
            }
        }
        
        stage('Fase 3: Pruebas / Test') {
            steps {
                echo 'Ejecutando pruebas unitarias automatizadas...'
                sh 'echo "Pruebas superadas al 100%."'
            }
        }
    }
}

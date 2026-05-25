pipeline {
    agent any

    stages {
        stage('Fase 1: Checkout de Código') {
            steps {
                echo 'Clonando el repositorio fuente actualizado desde GitHub...'
                checkout scm
            }
        }
        
        stage('Fase 2: Compilación / Build') {
            steps {
                echo 'Ejecutando compilación del código fuente y descargando dependencias...'
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Fase 3: Pruebas Unitarias / Test') {
            steps {
                echo 'Invocando la batería de pruebas automatizadas JUnit...'
                sh 'mvn test -Djava.awt.headless=true'
            }
        }

        stage('Fase 4: Empaquetado / Artifact Distribution') {
            steps {
                echo 'Construyendo el artefacto distribuible final (.JAR)...'
                sh 'mvn package -DskipTests'
                
                echo 'Estructura de archivos generada en el espacio de trabajo:'
                sh 'ls -l target/'
            }
        }
    }
}

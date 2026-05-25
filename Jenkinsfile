pipeline {
    agent any

    stages {
        stage('Fase 1: Preparar Entorno (EC2)') {
            steps {
                echo 'Actualizando gestor de paquetes e instalando componentes globales...'
                sh 'sudo apt-get update -y && sudo apt-get install -y maven'
            }
        }

        stage('Fase 2: Checkout de Código') {
            steps {
                echo 'Clonando el repositorio fuente actualizado desde GitHub...'
                checkout scm
            }
        }
        
        stage('Fase 3: Compilación / Build') {
            steps {
                echo 'Ejecutando compilación del código fuente y descargando dependencias...'
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Fase 4: Pruebas Unitarias / Test') {
            steps {
                echo 'Invocando la batería de pruebas automatizadas JUnit...'
                // Añadimos el modo headless para que JavaFX no reviente al no encontrar un monitor en AWS
                sh 'mvn test -Djava.awt.headless=true'
            }
        }

        stage('Fase 5: Empaquetado / Artifact Distribution') {
            steps {
                echo 'Construyendo el artefacto distribuible final (.JAR)...'
                // Empaquetamos saltándonos los tests aquí porque ya los validamos con éxito en la Fase 4
                sh 'mvn package -DskipTests'
                
                echo 'Estructura de archivos generada en el espacio de trabajo:'
                sh 'ls -l target/'
            }
        }
    }
}

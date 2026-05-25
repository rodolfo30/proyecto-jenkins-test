pipeline {
    agent any

    stages {
        stage('Fase 1: Preparar Entorno (EC2)') {
            steps {
                echo 'Actualizando gestor de paquetes e instalando componentes globales...'
                // Instala Maven directamente en el sistema de la EC2 si no se encuentra presente
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
                // Limpia el espacio y compila las clases principales sin ejecutar los tests
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Fase 4: Pruebas Unitarias / Test') {
            steps {
                echo 'Invocando la batería de pruebas automatizadas JUnit...'
                // Ejecuta los componentes de prueba reales del proyecto
                sh 'mvn test'
            }
        }

        stage('Fase 5: Empaquetado / Artifact Distribution') {
            steps {
                echo 'Construyendo el artefacto distribuible final (.JAR)...'
                // Compila, testea y empaqueta el binario final en la carpeta target/
                sh 'mvn package'
                
                echo 'Estructura de archivos generada en el espacio de trabajo:'
                sh 'ls -l target/'
            }
        }
    }
}

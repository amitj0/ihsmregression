pipeline {

    /***********************
     * GLOBAL CONFIG
     ***********************/
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '30'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
    }

    /***********************
     * NIGHTLY TRIGGER
     ***********************/
    triggers {
        cron('H 23 * * *')     // Runs nightly ~11 PM
    }

    /***********************
     * TOOLS
     ***********************/
    tools {
        maven 'MAVEN1'
    }

    /***********************
     * GLOBAL VARIABLES
     ***********************/
    environment {
        PROJECT     = "IHSM UNIVERSITY AUTOMATION"
        REPORT_NAME = "IHSM University Extent Report"
        DASHBOARD_NOTE = "The Extent Report dashboard has been updated. Please open the report and press “D” to view the dashboard data."
    }

    /***********************
     * USER PARAMETERS
     ***********************/
    parameters {

        choice(name: 'ENV',
               choices: ['QA', 'UAT', 'PROD'],
               description: '🌍 Target Environment')

        choice(name: 'BROWSER',
               choices: ['chrome', 'edge', 'firefox'],
               description: '🌐 Browser')

        choice(name: 'TEST_TYPE',
               choices: ['Regression', 'Smoke'],
               description: '🧪 Test Suite')
    }

    /***********************
     * PIPELINE STAGES
     ***********************/
    stages {

        stage('🎯 INITIALIZE') {
            steps {
                echo """
=================================================
PROJECT   : ${PROJECT}
ENV       : ${params.ENV}
BROWSER   : ${params.BROWSER}
TEST TYPE : ${params.TEST_TYPE}
=================================================
"""
            }
        }

        stage('🧹 CLEAN WORKSPACE') {
            steps {
                deleteDir()
            }
        }

        stage('📥 CHECKOUT CODE') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-pat',
                    url: 'https://github.com/amitj0/ihsmregression.git'
            }
        }

        stage('🔧 BUILD PROJECT') {
            steps {
                bat 'mvn -q clean compile'
            }
        }

        stage('🚀 EXECUTE TESTS') {
    steps {
        script {
            try {
                bat """
                mvn test ^
                -Dbrowser=${params.BROWSER} ^
                -Denv=${params.ENV} ^
                -Dsuite=${params.TEST_TYPE}
                """
            } catch (Exception e) {
                echo "Some tests failed. Marking build as UNSTABLE."
                currentBuild.result = 'UNSTABLE'
            }
        }
    }
}


        stage('📊 PUBLISH EXTENT REPORT') {
            steps {
                publishHTML(target: [
                    reportDir: 'reports',
                    reportFiles: '*.html',
                    reportName: REPORT_NAME,
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }

    /***********************
     * POST ACTIONS
     ***********************/
    post {

        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/*.html', allowEmptyArchive: true
        }

        success {
            emailext(
                to: 'ajangra@ismedusoftsol.com,jchhillar@ismedusoftsol.com,ankur@ismedusoftsol.com,amit@ismedusoftsol.com,corporate@ismedusoftsol.com,vpahwa@ismedusoftsol.com',
                subject: "✅ ${PROJECT} | SUCCESS | Build #${BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                attachmentsPattern: 'reports/*.html',

                body: """
                <h3 style="color:green;">ℹ️ ${DASHBOARD_NOTE}</h3>
                <h2 style="color:green;">Execution Successful</h2>

                <p><b>Project:</b> ${PROJECT}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><b>Browser:</b> ${params.BROWSER}</p>
                <p><b>Suite:</b> ${params.TEST_TYPE}</p>

                <p>
                  <a href="${BUILD_URL}">🔗 Open Jenkins Build</a><br>
                  <a href="${BUILD_URL}HTML_20Report/">📊 Open Extent Report</a>
                </p>

                <p><b>Attachments:</b></p>
                <ul>
                  <li>Console Log</li>
                  <li>Extent HTML Report</li>
                </ul>
                """
            )
        }
        
        unstable {
        emailext(
            to: 'ajangra@ismedusoftsol.com,jchhillar@ismedusoftsol.com,ankur@ismedusoftsol.com,amit@ismedusoftsol.com,corporate@ismedusoftsol.com,vpahwa@ismedusoftsol.com',
            subject: "⚠️ ${PROJECT} | UNSTABLE | Build #${BUILD_NUMBER}",
            mimeType: 'text/html',
            attachLog: true,
            attachmentsPattern: 'reports/*.html',
            body: """
            <h3 style="color:orange;">ℹ️ ${DASHBOARD_NOTE}</h3>
            <h2 style="color:orange;">Execution Completed with Some Test Failures</h2>

            <p><b>Project:</b> ${PROJECT}</p>
            <p><b>Environment:</b> ${params.ENV}</p>
            <p><b>Browser:</b> ${params.BROWSER}</p>
            <p><b>Suite:</b> ${params.TEST_TYPE}</p>

            <p>
              <a href="${BUILD_URL}">🔗 Open Jenkins Build</a><br>
              <a href="${BUILD_URL}HTML_20Report/">📊 Open Extent Report</a>
            </p>
            """
        )
    }

        failure {
            emailext(
                to: 'ajangra@ismedusoftsol.com,jchhillar@ismedusoftsol.com,ankur@ismedusoftsol.com,amit@ismedusoftsol.com,corporate@ismedusoftsol.com,vpahwa@ismedusoftsol.com',
                subject: "❌ ${PROJECT} | FAILED | Build #${BUILD_NUMBER}",
                mimeType: 'text/html',
                attachLog: true,
                attachmentsPattern: 'reports/*.html',

                body: """
                 <h3 style="color:red;">ℹ️ ${DASHBOARD_NOTE}</h3>
                <h2 style="color:red;">Execution Failed</h2>

                <p><b>Project:</b> ${PROJECT}</p>
                <p><b>Environment:</b> ${params.ENV}</p>
                <p><b>Browser:</b> ${params.BROWSER}</p>
                <p><b>Suite:</b> ${params.TEST_TYPE}</p>

                <p>
                  <a href="${BUILD_URL}">🔗 Open Jenkins Build</a><br>
                  <a href="${BUILD_URL}HTML_20Report/">📊 Open Extent Report</a>
                </p>

                <p><b>Attachments:</b></p>
                <ul>
                  <li>Console Log</li>
                  <li>Extent HTML Report</li>
                </ul>
                """
            )
        }
    }
}

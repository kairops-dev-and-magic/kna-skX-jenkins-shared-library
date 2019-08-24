FROM jenkins/jenkins:lts-alpine

ARG OPENJDK_VERSION=1.8
ARG MVN_VERSION=1.8

# install the OpenJDK 8 java runtime environment and curl
USER root
RUN apk update; \
  apk upgrade --available; \
  apk add --no-cache openjdk8 maven docker py-pip python-dev libffi-dev openssl-dev gcc libc-dev make; \
  pip install docker-compose; \
  rm -fr /var/cache/apk/*

ARG SONAR_SCANNER_VERSION=3.3.0.1492
RUN curl -s -L https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-${SONAR_SCANNER_VERSION}.zip -o sonarscanner.zip \
  && unzip -qq sonarscanner.zip -d /opt \
  && rm -rf /opt/sonarscanner.zip \
  && mv /opt/sonar-scanner-${SONAR_SCANNER_VERSION} /opt/sonar-scanner

RUN mkdir /credentials
RUN chown jenkins:jenkins /credentials

USER jenkins

# Disable Setup Wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true"

COPY resources/online/kairops/kna/skX/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

RUN mkdir -p ${JENKINS_HOME}/workflow-libs/static
COPY .git/ ${JENKINS_HOME}/workflow-libs/static/.git
COPY resources/ ${JENKINS_HOME}/workflow-libs/static/resources
COPY src/ ${JENKINS_HOME}/workflow-libs/static/src
COPY vars/ ${JENKINS_HOME}/workflow-libs/static/vars

COPY resources/online/kairops/kna/skX/*.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY resources/online/kairops/kna/skX/*.jenkinsfile /usr/share/jenkins/ref/init.groovy.d/

VOLUME /credentials


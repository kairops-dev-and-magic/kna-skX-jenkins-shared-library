FROM jenkins/jenkins:lts-alpine

# Disable Setup Wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY resources/online/kairops/kna/skX/*.groovy /usr/share/jenkins/ref/init.groovy.d/

COPY resources/online/kairops/kna/skX/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
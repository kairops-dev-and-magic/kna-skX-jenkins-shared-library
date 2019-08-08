import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation
import hudson.model.*
import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.TriggersConfig
import hudson.tools.*


def jenkins = Jenkins.get()


def jdkInstallations = [
        "Default" : "/usr/lib/jvm/java-1.8-openjdk/"
]

def mvnInstallations = [
        "Default" : "/usr/share/java/maven-3/"
]

def sonarInstallations = [
        "SonarQubeScanner" : "/opt/sonar-scanner"
]

def jenkinsJDKInstallations = []
for (installation in jdkInstallations) {
    def jenkinsJDKInstallation = new JDK(installation.key, installation.value)
    jenkinsJDKInstallations.push(jenkinsJDKInstallation)
}

def jdkDescriptor = jenkins.getDescriptor("hudson.model.JDK")
jdkDescriptor.setInstallations(jenkinsJDKInstallations.toArray(new JDK[0]))
jdkDescriptor.save()


def mavenPlugin = jenkins.getExtensionList(Maven.DescriptorImpl.class)[0]
for (installation in mvnInstallations) {
    def jenkinsMvnInstallation =  newMavenInstall = new MavenInstallation(installation.key, installation.value)
    mavenPlugin.installations += jenkinsMvnInstallation
}
mavenPlugin.save();


// Required environment variables
def sonar_name = "Default"
def sonar_server_url = "http://sonarqube-kna.kairops.online:9000"
def sonar_auth_token = "<token>"
def sonar_mojo_version = ''
def sonar_additional_properties = ''
def sonar_triggers = new TriggersConfig()
def sonar_additional_analysis_properties = ''
def sonar_runner_version = '3.3.0.1492'


println("Configuring SonarQube...")
// Get the GlobalConfiguration descriptor of SonarQube plugin.
def SonarGlobalConfiguration sonar_conf = jenkins.getDescriptor(SonarGlobalConfiguration.class)

def sonar_inst = new SonarInstallation(
        sonar_name,
        sonar_server_url,
        sonar_auth_token,
        sonar_mojo_version,
        sonar_additional_properties,
        sonar_triggers,
        sonar_additional_analysis_properties)

def sonar_installations = sonar_conf.getInstallations()

sonar_installations += sonar_inst
sonar_conf.setInstallations((SonarInstallation[]) sonar_installations)
sonar_conf.save()

println("Configuring SonarRunner...")
def desc_SonarRunnerInst = jenkins.getDescriptor("hudson.plugins.sonar.SonarRunnerInstallation")
def sonar_runner_installations = desc_SonarRunnerInst.getInstallations()
for (installation in sonarInstallations) {
    def sonarRunner_inst = new SonarRunnerInstallation(installation.key, installation.value, null)
    sonar_runner_installations += sonarRunner_inst
    desc_SonarRunnerInst.setInstallations((SonarRunnerInstallation[]) sonar_runner_installations)
}

desc_SonarRunnerInst.save()

jenkins.save()
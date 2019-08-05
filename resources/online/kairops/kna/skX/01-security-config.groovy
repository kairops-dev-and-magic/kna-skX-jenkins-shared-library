#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule
import hudson.security.csrf.*

def instance = Jenkins.getInstance()
def configuration = JenkinsLocationConfiguration.get()

instance.setDisableRememberMe(true)
instance.setNumExecutors(1)
instance.setSystemMessage("Bienvenidos a KNA SKX Jenkins Shared Library Environment")

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'admin'
hudsonRealm.createAccount(adminUsername, adminPassword )
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

// https://wiki.jenkins.io/display/JENKINS/CSRF+Protection
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))


configuration.setUrl("http://jenkins-kna.kairops.online:8080")
configuration.setAdminAddress("c3did@kairops.online")


configuration.save()
instance.save()

Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)
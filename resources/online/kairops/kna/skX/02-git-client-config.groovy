// 02-git-client-config.groovy
// https://stackoverflow.com/questions/33613868/how-to-store-secret-text-or-file-using-groovy

import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import hudson.plugins.sshslaves.*
import jenkins.model.Jenkins

// Get a handle on our Jenkins instance
def jenkins = Jenkins.get()

// Get the config descriptor for the overall Git config
def gitSCMDescriptor = jenkins.getDescriptor("hudson.plugins.git.GitSCM")

// Set the username and email for git interactions
def gitUser = System.getenv('GIT_USERNAME') ?: ''
def gitUserDomain = System.getenv('GIT_USERNAME_DOMAIN') ?: ''
gitSCMDescriptor.setGlobalConfigName(gitUser)
gitSCMDescriptor.setGlobalConfigEmail(gitUser + "@" + gitUserDomain)

// Save the descriptor
gitSCMDescriptor.save()
println("[INFO] Git global configuration complete!")

// Configure SSH key for Git User
String gitSSHPrivKeyFile = System.getenv('GIT_SSH_PRIVATE_KEY_FILE')

if  (new File(gitSSHPrivKeyFile).exists()) {
    def key = new File(gitSSHPrivKeyFile).text
    gitSSHPrivateKey = new BasicSSHUserPrivateKey(
            CredentialsScope.GLOBAL,
            "git-ssh-key",
            gitUser,
            new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(key),
            "",
            gitUser + " SSH key credentials")

   // Get our existing Credentials Store
    def credentialsStore = jenkins.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

    // get credentials domain
    def domain = Domain.global()

    credentialsStore.addCredentials(domain, gitSSHPrivateKey)

    // TODO  - Delete ssh key file
    println("[INFO] Git SSH key credentials configuration complete!")

} else {
    println("[WARN] No SSH key Credentials file found for [" + gitUser + "]")
}



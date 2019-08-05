// 02-configure-git-client.groovy
// Thanks to chrish:
// https://stackoverflow.com/questions/33613868/how-to-store-secret-text-or-file-using-groovy
import jenkins.model.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.domains.*;
import com.cloudbees.plugins.credentials.*;


// Read our values into strings from the volume mount
privKeyText = "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "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\n" +
        "-----END OPENSSH PRIVATE KEY-----"
//passPhraseText = new File('/secure/git-ssh-key.pw').text.trim()
sshUserText = "c3did"

// Get a handle on our Jenkins instance
def jenkins = Jenkins.getInstance()

// Define the security domain. We're making these global but they can also
// be configured in a more restrictive manner. More on that later
def domain = Domain.global()

// Get our existing Credentials Store
def store = jenkins.getExtensionList(
        'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
)[0].getStore()

// Create a new BasicSSHUserPrivateKey object with our values
gitHubSSHKey = new BasicSSHUserPrivateKey(
        CredentialsScope.GLOBAL,
        "git-ssh-key",
        sshUserText,
        new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(privKeyText),
        "",
        "GitHub c3did SSH credentials"
)

// Add the new object to the credentials store
store.addCredentials(domain, gitHubSSHKey)

// Get the config descriptor for the overall Git config
def desc = jenkins.getDescriptor("hudson.plugins.git.GitSCM")

// Set the username and email for git interactions
desc.setGlobalConfigName("${sshUserText}")
desc.setGlobalConfigEmail("${sshUserText}@kairops.online")

// Save the descriptor
desc.save()

// Echo out (or log if you like) that we've done something
println("INFO: GitHub Credentials and Configuration complete!")
#!groovy

import hudson.plugins.git.*
import hudson.plugins.git.extensions.*
import hudson.plugins.git.extensions.impl.*
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

def PIPELINES_DIR = new File(getClass().protectionDomain.codeSource.location.path).parent

// get Jenkins instance
Jenkins jenkins = Jenkins.get()

def TEST_PIPELINES = [
        "MvnPipeline Test Job" : "MvnPipeline.jenkinsfile"
]

for (pipeline in TEST_PIPELINES) {

    String fileContents = new File(PIPELINES_DIR + "/" + pipeline.value).text

    // define SCM flow
    def flowDefinition = new CpsFlowDefinition(fileContents,false)

    // create the job
    def job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(jenkins, pipeline.key)

    // define job type
    job.definition = flowDefinition

    // save to disk
    jenkins.save()
}

jenkins.reload()
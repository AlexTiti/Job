package com.alex.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginModule implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.android.applicetionVariants.all{
        }
        println "==========================="
        println "-------PluginModule--------"
        println "==========================="
    }
}
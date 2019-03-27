package com.alex.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginTest implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "-----------------------------------"
        println "This Is  PluginTest In BuildSrc Module !"
    }
}
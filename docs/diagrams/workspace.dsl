workspace "Frege Language Server" {
    model {
        fregeDeveloper = person "Frege Developer"
        fregeIde  = softwareSystem "Frege Integrated Development Environment" "Visualises Frege language features to develop Frege applications faster." {
            textEditor          = container "Text Editor" "Allows basic text editing of documents."
            fregeExtension      = container "Frege Language Extension" "Adds Frege specific language features to a text editor."
            fregeLanguageServer = container "Frege Language Server" "Provides Frege specific language features." {
                lsp        = component "LSP" "Receives and sends notifications, requests and responses according to the language server protocol (LSP)."
                compile    = component "Compile" "Runs the Frege compiler and manages all available compiler information, called global."
                diagnostic = component "Diagnostic" "Extracts all available errors and warnings from the compiler global and returns them as LSP diagnostics."
                hover      = component "Hover" "Extracts all available type signatures of functions and variables from the compiler global and returns them as LSP hover."
                project    = component "Project" "Configures the Frege project with information such as external dependencies from Gradle Build Tool if available."
            }
        }
        fregeApplication = softwareSystem "Application" "The Frege application to be developed."
        fregeCompiler    = softwareSystem "Frege Compiler" "Transpiles Frege code into Java code."
        buildTool  = softwareSystem "Gradle Build Tool" "Automates the process of creating an exectuable from source code." {
            core        = container "Gradle Core" "Provides the standard Gradle build tool functionality."
            fregePlugin = container "Frege Gradle Plugin" "Adds tasks to create, compile, run, REPL and test a Frege project to Gradle."
        }

        textEditor          -> fregeExtension "activates and uses"
        fregeExtension      -> fregeLanguageServer "requests Frege language feature from" "Language Server Protocol (LSP)"
        fregeExtension      -> textEditor "visualises Frege language features in"
        fregeDeveloper      -> fregeApplication "develops"
        fregeDeveloper      -> fregeIde "writes source code in"
        fregeDeveloper      -> buildTool "configures and builds application with"
        fregeLanguageServer -> fregePlugin "extracts specific Frege application configuration"
        buildTool           -> fregeCompiler "creates an executable Frege application for the Java Virtual Machine with"
        fregeLanguageServer -> fregeCompiler "extracts Frege language features from"
        lsp                 -> compile "on new source code changes"
        lsp                 -> hover "on hover request"
        lsp                 -> diagnostic "on new source code changes"
        lsp                 -> project "on intialize"
        compile             -> fregeCompiler "extracts compiler global from"
        project             -> fregePlugin "extracts Frege project config from"
        project             -> compile "configures project compiler options"
    }

    views {
        systemlandscape "SystemLandscape" {
            include * 
            autoLayout lr
        }

        systemContext fregeIde "SystemContext" {
            include *
            autoLayout lr
        }

        container fregeIde "Language-Server-Container-Diagram" {
            include *
            autoLayout lr
        }

        container buildTool "buildTool" {
            include *
            autoLayout lr
        }

        component fregeLanguageServer "Components" {
            include *
            autoLayout lr
        }
        styles {
            element "Person" {
                color #ffffff
                shape Person
                background #08427b
            }
            relationship "Relationship" {
                fontSize 32
            }

            element "Element" {
                fontSize 26
            }
        }
    }
}
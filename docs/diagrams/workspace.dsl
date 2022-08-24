workspace "Frege Language Server" {
    model {
        developer  = person "Developer"
        textEditor = softwareSystem "Text Editor" "Allows basic text editing." "Existing System"
        fregeIde   = softwareSystem "Frege Integrated Development Environment" "Provides Frege language features to develop Frege applications faster." {
            fregeExtension      = container "Frege Language Extension" "Adds Frege specific language features to a text editor."
            fregeGradlePlugin   = container "Frege Gradle Plugin" "Adds tasks to create, compile, run, REPL and test a Frege project to Gradle."
            fregeLanguageServer = container "Frege Language Server" "Provides Frege specific language features." {
                lsp        = component "LSP" "Receives and sends notifications, requests and responses according to the language server protocol (LSP)."
                compile    = component "Compile" "Runs the Frege compiler and manages all available compiler information, called global."
                diagnostic = component "Diagnostic" "Extracts all available errors and warnings from the compiler global and returns them as LSP diagnostics."
                hover      = component "Hover" "Extracts all available type signatures of functions and variables from the compiler global and returns them as LSP hover."
                project    = component "Project" "Configures the Frege project with information such as external dependencies from Gradle Build Tool if available."
            }
        }
        fregeApplication = softwareSystem "Application" "The application to be developed." "Existing System"
        fregeCompiler    = softwareSystem "Frege Compiler" "Transpiles Frege code into Java code." "Existing System"
        buildTool  = softwareSystem "Build Tool" "Automates the process of creating an exectuable from source code." "Existing System"

        fregeGradlePlugin   -> buildTool "adds Frege support to"
        fregeExtension      -> fregeLanguageServer "requests Frege language feature from" "Language Server Protocol (LSP)"
        fregeExtension      -> textEditor "visualises Frege language features in"
        developer           -> fregeApplication "develops"
        developer           -> textEditor "writes source code in"
        developer           -> buildTool "configures and builds application with"
        fregeLanguageServer -> fregeGradlePlugin "extracts specific Frege application configuration"
        buildTool           -> fregeCompiler "creates an executable application with"
        fregeLanguageServer -> fregeCompiler "extracts Frege language features from"
        lsp                 -> compile "on new source code changes"
        lsp                 -> hover "on hover request"
        lsp                 -> diagnostic "on new source code changes"
        lsp                 -> project "on intialize"
        compile             -> fregeCompiler "extracts compiler global from"
        project             -> fregeGradlePlugin "extracts Frege project config from"
        project             -> compile "configures project compiler options"
    }

    views {
        systemlandscape "SystemLandscape" {
            include *
            //autoLayout lr
        }

        systemContext fregeIde "SystemContext" {
            include *
            autoLayout lr
        }

        container fregeIde "Language-Server-Container-Diagram" {
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

            element "Existing System" {
                background #999999
                color #ffffff
            }

            element "Software System" {
                background #1168bd
                color #ffffff
            }
        }
    }
}
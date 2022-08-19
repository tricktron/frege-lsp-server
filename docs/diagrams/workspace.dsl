workspace "Frege Language Server" {
    model {
        fregeDeveloper = person "Frege Developer"
        fregeIde  = softwareSystem "Integrated Development Environment (IDE) for Frege" "Helps developers to develop Frege applications faster by providing extra language features besides basic text editing." {
            textEditor          = container "Text Editor" "Allows basic text editing of documents."
            fregeExtension      = container "Frege Language Extension" "Adds Frege specific language features to a text editor."
            fregeLanguageServer = container "Frege Language Server" "Provides Frege specific language features." {
                lsp        = component "LSP" "Receives and sends notifications and requests according to the language server protocol (LSP)."
                compile    = component "Compile" "Runs the Frege compiler and manages all available compiler information, called global."
                diagnostic = component "Diagnostic" "Extracts all available errors and warnings from the compiler global and returns them as LSP diagnostics."
                hover      = component "Hover" "Extracts all available type signatures of functions and variables from the compiler global and returns them as LSP hover."
                project    = component "Project" "Extract project specific information such as external dependencies and returns them as compiler options."
            }
        }
        fregeApplication = softwareSystem "Application" "The Frege application to be developed."
        fregeCompiler    = softwareSystem "Frege Compiler" "Transpiles Frege code into Java code."
        gradleBuildTool  = softwareSystem "Gradle Build Tool" "Automates the process of creating an exectuable from source code." {
            core        = container "Gradle Core" "Provides the well known Gradle build tool functionality."
            fregePlugin = container "Frege Plugin" "Automates the creation and compilation of Frege projects."
        }

        textEditor          -> fregeExtension "activates and uses"
        fregeExtension      -> fregeLanguageServer "requests Frege language feature from" "Language Server Protocol (LSP)"
        fregeExtension      -> textEditor "visualises Frege language features in"
        fregeDeveloper      -> fregeApplication "develops"
        fregeDeveloper      -> fregeIde "writes source code in"
        fregeDeveloper      -> gradleBuildTool "configures and builds application with"
        fregeLanguageServer -> fregePlugin "extracts Frege application/project specific configuration"
        gradleBuildTool     -> fregeCompiler "creates an executable Frege application for the Java Virtual Machine with"
        fregeLanguageServer -> fregeCompiler "extracts Frege language features from"
        lsp                 -> compile "Notify the compile service on new source code changes"
        lsp                 -> hover "Trigger the hover service on hover requests and return the hover results"
        lsp                 -> diagnostic "Notify the diagnostic service on new source code changes"
        lsp                 -> project "Notify the project service when the Frege extension starts"
        hover               -> compile "Get current compiler global"
        diagnostic          -> compile "Get current compiler global"
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

        container gradleBuildTool "buildTool" {
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
                fontSize 22
                shape Person
                background #08427b
            }
        }
    }
}
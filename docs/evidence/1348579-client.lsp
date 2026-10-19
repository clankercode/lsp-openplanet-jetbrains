Content-Length: 4024

{"jsonrpc":"2.0","id":"1","method":"initialize","params":{"processId":1345122,"rootPath":"/home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo","rootUri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo","initializationOptions":{},"capabilities":{"workspace":{"applyEdit":true,"workspaceEdit":{"documentChanges":true,"resourceOperations":["create","delete","rename"]},"didChangeConfiguration":{"dynamicRegistration":true},"didChangeWatchedFiles":{"dynamicRegistration":true},"symbol":{},"executeCommand":{"dynamicRegistration":true},"workspaceFolders":true,"configuration":true,"semanticTokens":{"refreshSupport":true},"codeLens":{"refreshSupport":true},"fileOperations":{"didCreate":true,"didRename":true,"willRename":true,"didDelete":true,"dynamicRegistration":true},"inlayHint":{"refreshSupport":true},"diagnostics":{"refreshSupport":true}},"textDocument":{"synchronization":{"willSave":true,"willSaveWaitUntil":true,"didSave":true},"completion":{"completionItem":{"snippetSupport":true,"documentationFormat":["markdown","plaintext"],"deprecatedSupport":true,"resolveSupport":{"properties":["documentation","detail","additionalTextEdits"]},"insertTextModeSupport":{"valueSet":[1,2]},"labelDetailsSupport":true},"completionList":{"itemDefaults":["editRange"]},"dynamicRegistration":true},"hover":{"contentFormat":["markdown","plaintext"],"dynamicRegistration":true},"signatureHelp":{"signatureInformation":{"parameterInformation":{"labelOffsetSupport":true}},"dynamicRegistration":true},"references":{"dynamicRegistration":true},"documentHighlight":{"dynamicRegistration":true},"documentSymbol":{"symbolKind":{"valueSet":[18,17,5,14,9,10,22,24,8,1,12,11,20,6,2,3,21,16,19,25,4,7,15,23,26,13]},"hierarchicalDocumentSymbolSupport":true,"dynamicRegistration":true},"formatting":{"dynamicRegistration":true},"rangeFormatting":{"dynamicRegistration":true},"declaration":{"linkSupport":true,"dynamicRegistration":true},"definition":{"linkSupport":true,"dynamicRegistration":true},"typeDefinition":{"linkSupport":true,"dynamicRegistration":true},"implementation":{"linkSupport":true,"dynamicRegistration":true},"codeAction":{"codeActionLiteralSupport":{"codeActionKind":{"valueSet":["quickfix","refactor","refactor.extract","refactor.inline","refactor.rewrite","source","source.organizeImports"]}},"dataSupport":true,"resolveSupport":{"properties":["edit"]},"dynamicRegistration":true},"codeLens":{"dynamicRegistration":true},"documentLink":{"dynamicRegistration":true},"colorProvider":{"dynamicRegistration":true},"rename":{"prepareSupport":true,"dynamicRegistration":true},"publishDiagnostics":{"relatedInformation":true,"tagSupport":{"valueSet":[1,2]},"codeDescriptionSupport":true,"dataSupport":true},"foldingRange":{"dynamicRegistration":true},"typeHierarchy":{"dynamicRegistration":true},"callHierarchy":{"dynamicRegistration":true},"semanticTokens":{"requests":{"range":false,"full":true},"tokenTypes":["namespace","type","class","enum","interface","struct","typeParameter","parameter","variable","property","enumMember","event","function","method","macro","keyword","modifier","comment","string","number","regexp","operator","decorator","label"],"tokenModifiers":["declaration","definition","readonly","static"],"formats":["relative"],"multilineTokenSupport":true,"serverCancelSupport":true,"dynamicRegistration":true},"inlayHint":{"dynamicRegistration":true},"diagnostic":{"relatedDocumentSupport":true,"dynamicRegistration":true},"inlineCompletion":{"dynamicRegistration":true}},"window":{"workDoneProgress":true,"showMessage":{"messageActionItem":{"additionalPropertiesSupport":false}},"showDocument":{"support":true}},"general":{"staleRequestSupport":{"cancel":true,"retryOnContentModified":[]},"positionEncodings":["utf-16"]}},"clientInfo":{"name":"IntelliJ IDEA 2024.3.6","version":"IntelliJ IDEA (build IC-243.26574.91)"},"trace":"off","workspaceFolders":[{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo","name":"diagnostics-demo"}]}}Content-Length: 52

{"jsonrpc":"2.0","method":"initialized","params":{}}Content-Length: 444

{"jsonrpc":"2.0","method":"textDocument/didOpen","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as","languageId":"angelscript","version":1,"text":"// Original minimal Openplanet example for editor integration verification.\n// The missing helper below is intentional: inspect its live LSP diagnostic.\nvoid Main() {\n    uint laps \u003d 3;\n    MissingFunction(laps);\n}\n"}}}Content-Length: 254

{"jsonrpc":"2.0","id":"2","method":"textDocument/inlayHint","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":0,"character":0},"end":{"line":6,"character":0}}}}Content-Length: 64

{"jsonrpc":"2.0","method":"$/cancelRequest","params":{"id":"2"}}Content-Length: 254

{"jsonrpc":"2.0","id":"3","method":"textDocument/inlayHint","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":0,"character":0},"end":{"line":6,"character":0}}}}Content-Length: 64

{"jsonrpc":"2.0","method":"$/cancelRequest","params":{"id":"3"}}Content-Length: 185

{"jsonrpc":"2.0","id":"4","method":"textDocument/documentSymbol","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 183

{"jsonrpc":"2.0","id":"5","method":"textDocument/foldingRange","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 254

{"jsonrpc":"2.0","id":"6","method":"textDocument/inlayHint","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":0,"character":0},"end":{"line":6,"character":0}}}}Content-Length: 190

{"jsonrpc":"2.0","id":"7","method":"textDocument/semanticTokens/full","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 184

{"jsonrpc":"2.0","id":"8","method":"textDocument/documentColor","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 300

{"jsonrpc":"2.0","id":"9","method":"textDocument/codeAction","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":6,"character":0},"end":{"line":6,"character":0}},"context":{"diagnostics":[],"triggerKind":2}}}Content-Length: 186

{"jsonrpc":"2.0","id":"10","method":"textDocument/documentSymbol","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 255

{"jsonrpc":"2.0","id":"11","method":"textDocument/inlayHint","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":0,"character":0},"end":{"line":6,"character":0}}}}Content-Length: 184

{"jsonrpc":"2.0","id":"12","method":"textDocument/foldingRange","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 191

{"jsonrpc":"2.0","id":"13","method":"textDocument/semanticTokens/full","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 185

{"jsonrpc":"2.0","id":"14","method":"textDocument/documentColor","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"}}}Content-Length: 301

{"jsonrpc":"2.0","id":"15","method":"textDocument/codeAction","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":6,"character":0},"end":{"line":6,"character":0}},"context":{"diagnostics":[],"triggerKind":2}}}Content-Length: 346

{"jsonrpc":"2.0","method":"textDocument/didOpen","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml","languageId":"toml","version":1,"text":"[meta]\nname \u003d \"Openplanet LSP Demo\"\nauthor \u003d \"clankercode\"\ncategory \u003d \"Utilities\"\nversion \u003d \"0.1.0\"\n"}}}Content-Length: 227

{"jsonrpc":"2.0","id":"16","method":"textDocument/documentHighlight","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"},"position":{"line":0,"character":0}}}Content-Length: 187

{"jsonrpc":"2.0","id":"17","method":"textDocument/documentColor","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"}}}Content-Length: 193

{"jsonrpc":"2.0","id":"18","method":"textDocument/semanticTokens/full","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"}}}Content-Length: 257

{"jsonrpc":"2.0","id":"19","method":"textDocument/inlayHint","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"},"range":{"start":{"line":0,"character":0},"end":{"line":5,"character":0}}}}Content-Length: 188

{"jsonrpc":"2.0","id":"20","method":"textDocument/documentSymbol","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"}}}Content-Length: 303

{"jsonrpc":"2.0","id":"21","method":"textDocument/codeAction","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/info.toml"},"range":{"start":{"line":0,"character":0},"end":{"line":0,"character":0}},"context":{"diagnostics":[],"triggerKind":2}}}Content-Length: 225

{"jsonrpc":"2.0","id":"22","method":"textDocument/documentHighlight","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"position":{"line":6,"character":0}}}Content-Length: 488

{"jsonrpc":"2.0","id":"23","method":"textDocument/codeAction","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":4,"character":4},"end":{"line":4,"character":19}},"context":{"diagnostics":[{"range":{"start":{"line":4,"character":4},"end":{"line":4,"character":19}},"severity":1,"source":"openplanet-lsp","message":"undefined identifier `MissingFunction`"}],"only":["quickfix"],"triggerKind":2}}}Content-Length: 226

{"jsonrpc":"2.0","id":"24","method":"textDocument/documentHighlight","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"position":{"line":4,"character":18}}}Content-Length: 303

{"jsonrpc":"2.0","id":"25","method":"textDocument/codeAction","params":{"textDocument":{"uri":"file:///home/xertrov/src/lsp-openplanet-jetbrains/examples/diagnostics-demo/Main.as"},"range":{"start":{"line":4,"character":18},"end":{"line":4,"character":18}},"context":{"diagnostics":[],"triggerKind":2}}}
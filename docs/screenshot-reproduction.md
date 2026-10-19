# Real editor capture

Use a private desktop / Xvfb, never a rendered imitation of the IDE. The included `examples/diagnostics-demo` is original source with intentional mistakes. No fixture type database is needed or installed.

1. Download and unpack IntelliJ Community 2024.3.6 and LSP4IJ 0.21.0. Build this plugin with JDK 21 using the README command.
2. Create a fresh temporary profile with `config`, `system`, `plugins`, and `log` directories. Unzip LSP4IJ and our built plugin ZIP into `plugins` (each ZIP already contains its own top-level folder).
3. Create an `idea.properties` with absolute `idea.config.path`, `idea.system.path`, `idea.plugins.path`, and `idea.log.path` pointing inside that temporary profile. Set `IDEA_PROPERTIES` to that file and `IDEA_VM_OPTIONS` to a copy of the IDE's VM options with `-XX:ActiveProcessorCount=2` appended.
4. Start `Xvfb :117 -screen 0 1440x1000x24 -nolisten tcp` and run `DISPLAY=:117 <idea>/bin/idea.sh <repo>/examples/diagnostics-demo`. Use an unused display if 117 is occupied. Keep the processes alive while interacting; stop both after capturing.
5. Complete first-run setup and open `Main.as`. Configure the installed server's absolute path under Settings → Languages & Frameworks → Openplanet. Open the Problems pane and inspect the actual diagnostics; wait for indexing and server initialization.
6. Capture the desktop with `DISPLAY=:117 import -window root editor.png`. Inspect the PNG and a crop of the diagnostics. Do not paint additional diagnostics or UI into the image.

Optional raw-protocol proof: set `OPENPLANET_LSP_CAPTURE_SERVER` to the actual installed server and `OPENPLANET_LSP_CAPTURE_DIR` to a temporary log directory before starting the IDE. Set Openplanet's executable to the absolute `scripts/capture-lsp.py` path. This test-only relay records raw stdin/stdout and forwards them unmodified to the real server with `--lsp`. Inspect `*-client.lsp` / `*-server.lsp` with `scripts/inspect-lsp-trace.py`. This is a verification harness, not a runtime dependency.

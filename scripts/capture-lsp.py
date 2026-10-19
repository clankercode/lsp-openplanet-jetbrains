#!/usr/bin/env python3
"""Test-only transparent recorder. Set both env vars, then use as server path.
No messages are generated or modified. The real server receives all arguments.
"""
import os
import signal
import subprocess
import sys
import threading
from pathlib import Path

server = os.environ["OPENPLANET_LSP_CAPTURE_SERVER"]
directory = Path(os.environ["OPENPLANET_LSP_CAPTURE_DIR"])
directory.mkdir(parents=True, exist_ok=True)
prefix = directory / str(os.getpid())
child = subprocess.Popen([server, *sys.argv[1:]], stdin=subprocess.PIPE, stdout=subprocess.PIPE)


def stop(signum, _frame):
    if child.poll() is None:
        child.send_signal(signum)


def pump(source, target, filename):
    with filename.open("wb", buffering=0) as log:
        while chunk := source.read1(65536):
            log.write(chunk)
            try:
                target.write(chunk)
                target.flush()
            except BrokenPipeError:
                break
    try:
        target.close()
    except BrokenPipeError:
        pass


signal.signal(signal.SIGTERM, stop)
signal.signal(signal.SIGINT, stop)
threading.Thread(target=pump, args=(sys.stdin.buffer, child.stdin, Path(str(prefix) + "-client.lsp")), daemon=True).start()
try:
    pump(child.stdout, sys.stdout.buffer, Path(str(prefix) + "-server.lsp"))
finally:
    if child.poll() is None:
        child.terminate()
    try:
        child.wait(timeout=5)
    except subprocess.TimeoutExpired:
        child.kill()
        child.wait()
sys.exit(child.returncode)

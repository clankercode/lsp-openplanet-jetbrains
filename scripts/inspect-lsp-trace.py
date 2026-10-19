#!/usr/bin/env python3
"""Print relevant real-editor messages from capture-lsp.py's raw streams."""
import json
import sys
from pathlib import Path


def messages(data):
    while data:
        header, sep, remaining = data.partition(b"\r\n\r\n")
        if not sep:
            raise ValueError("Truncated LSP header")
        fields = dict(line.split(b":", 1) for line in header.split(b"\r\n"))
        length = int(fields[b"Content-Length"].strip())
        if len(remaining) < length:
            raise ValueError("Truncated LSP message")
        yield json.loads(remaining[:length])
        data = remaining[length:]


for name in sys.argv[1:]:
    for message in messages(Path(name).read_bytes()):
        if message.get("method") in {"initialize", "textDocument/didOpen", "textDocument/publishDiagnostics", "workspace/didChangeWorkspaceFolders"}:
            print(json.dumps({"trace": Path(name).name, **message}, ensure_ascii=False))

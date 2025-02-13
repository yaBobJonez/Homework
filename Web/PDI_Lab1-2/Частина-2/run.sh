#!/bin/bash

# Відкрити у браузері
xdg-open http://0.0.0.0:8000/Частина-2/xpath-tests.html
# Запустити вебсервер (^C щоб зупинити)
python3 -m http.server -d ..

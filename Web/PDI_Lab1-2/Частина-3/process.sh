#!/bin/bash

# Обробити XML процесором XSLT та згенерувати HTML сторінку
xsltproc -o result.html to-html.xsl data.xml

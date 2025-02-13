#!/bin/bash

# Перевірити сам DTD та XML за схемою DTD
xmllint --dtdvalid customs.dtd --valid data.xml --noout

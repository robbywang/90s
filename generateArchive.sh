#!/usr/bin/env bash
mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=org.ddu \
  -DartifactId=$1
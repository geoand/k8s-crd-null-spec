# Purpose

Demonstrate that applying a Custom Resource Definition with an empty spec field results in a NPE

# Run

## Prerequisites

Login into a cluster that has Istio (version 0.8 or higher) installed using the standard `oc login` command

## Run tests

`mvn test`

The above command will fail

## Manual oc test

The same scenario that is run above utilizing the Kubernetes Client, can be run manually using `oc` by doing:

`oc apply -f src/test/resources/istio-resource.yml`

When the command above is executed, the CRD is applied without any failures   
# CXF LB/Proxy Example

## Requirements

* Apache Maven 3.x (http://maven.apache.org)

Build the project source code

```
cd $PROJECT_ROOT
mvn clean install
```

## Running the example standalone (should be done in 3 separate terminal windows/tabs)

```
cd $PROJECT_ROOT/greeter-gateway
mvn spring-boot:run '-Dserver.port=8080'
cd $PROJECT_ROOT/greeter-english-impl
mvn spring-boot:run '-Dserver.port=9000'
cd $PROJECT_ROOT/greeter-german-impl
mvn spring-boot:run '-Dserver.port=9001'
cd $PROJECT_ROOT/greeter-spanish-impl
mvn spring-boot:run '-Dserver.port=9002'
```

## Running the example in OpenShift

```
oc new-project demo
cd $PROJECT_ROOT/greeter-gateway
mvn -P openshift clean install fabric8:deploy
cd $PROJECT_ROOT/greeter-english-impl
mvn -P openshift clean install fabric8:deploy
cd $PROJECT_ROOT/greeter-german-impl
mvn -P openshift clean install fabric8:deploy
cd $PROJECT_ROOT/greeter-spanish-impl
mvn -P openshift clean install fabric8:deploy
```

## Testing the code

Use your favorite WS testing tool (ie. SoapUI) and point it to http://localhost:8080/services/greeter.

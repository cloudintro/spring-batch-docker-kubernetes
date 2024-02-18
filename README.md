# spring-batch-docker-kubernetes
Cloud neutral Spring Boot Microservice with docker and k8s

```
task-manager calls task-processor to create and process the task

A task file can be uploaded to task-manager to create bulk tasks

task-processor process the task using the batch job and pdates the task status in database
```

#### build docker image
> docker build . -t task-manager:latest

> docker build . -t task-processor:latest

#### Run containers
> docker run -d --name local-mongo --add-host=host.docker.internal:host-gateway --publish 27017:27017 mongo:latest

> docker run -d --name task-manager --add-host=host.docker.internal:host-gateway --publish 8081:8080 task-manager:latest

> docker run -d --name task-processor --add-host=host.docker.internal:host-gateway --publish 8091:8080 task-processor:latest

> index page: http://localhost:8091/task-processor/
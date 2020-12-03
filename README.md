# EDUCATIONAL PROJECT FOR LEARNING ELASTICSEARCH

The project consists of two modules:
- elasticsearch-docker: folder with scripts to set up ES on docker and send requests to ES
- elasticsearch-spring: a Spring Boot application with Elasticsearch API

## elasticsearch-docker
It is a folder primarily used for:
 - runnig a docker instance of elasticsearch
 - creating indexes
 - checking indexes/health
 - making simple calls to elasticsearch (something replicated in the spring boot application)

## elasticsearch-spring
The Spring Boot application comes with a default SampleDocumentRepository which
may be filled or not with some sample data (which can be set in application.properties).
That way, you can quickly request the data after the application has started.

If you don't want to use the test data, you can send your own documents
which will be indexed by using simple json method in EsSaverController.

You can save or retrieve data in two ways:
- Using a regular repository mapped to "/repository/**" controller
- Using other controllers which are specified in the different modules of the project:
saver.\*, getter.\*, aggregations.\*

For controllers: you could argue that the methods should return dtos and I agree with that.
However, this project is not connected to any backend/frontend that would filter the messages,
that's why most of the time I return SearchHit[] or SearchResponse. Otherwise, I would
create assure the most important data is only returned.

## Mock environment
A Spring Boot application contains a mock environment which was created by
testcontainers. 

**This is something I would not use in the regular project or live environment.**

However, since it's an educational project, I decided to let it quickly set up an elasticsearch
container at the startup of Spring Boot application.

You can turn it off and on in application.properties.

Also, because of that, I did not replicate the code of running testcontainers for tests.

### Things I learnt:
While working on this project 14-18.09.2020, I was:

- Setting up 3-node environment of ES at docker
- Learning the full theory about clusters, indices, shards (primary, replicas), documents, fields etc.
- Managing indices
- Learning kibana/kaizen
- Checking up health of ES
- Saving data to ES
- Retrieving data from ES by GET method
- Retrieving data from ES by SEARCH method
- Retrieving data from ES by SEARCH method with scrolling
- Retrieving data from ES by SEARCH method with aggregations (metric, bucket, matrix)
- Sorting and grouping data
- Managing text fields with keywords and fielddata
- Adding mock environment
- Adding auto-upload of mock data
- Using @Document and ElasticsearchRepository
- Creating tests with Elasticsearch

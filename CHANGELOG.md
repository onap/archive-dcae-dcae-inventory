# Change Log

## 1.0.0

2016-08-22

Changes:

* DCAE service type resource's data model expanded to have the fields: `serviceIds` and `serviceLocations`
* Underlying Postgres table `dcae_service_types` schema changed to store the new fields
* `GET /dcae-service-types` query interface expanded have the query parameters: `serviceId` and `serviceLocation`.

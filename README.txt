This uses JPA (with Hibernate as the JPA provider) to commit a distributed transaction
into two databases, customer and orders, as part of
a single XA transaction. Or so we hope.
Requires a full Java EE server, not just Tomcat or Jetty.

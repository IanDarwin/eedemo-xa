= eedemo-xa

This uses JPA to commit a distributed transaction
into two databases, customer and orders, as part of
a single XA transaction. Or so we hope.
Requires a full Java EE server, not just Tomcat or Jetty.

== Setup 

=== Create the databases

psql <<!xa
create role xademo login password 'ex eh demo?';
create database xademo_customers owner xademo;
create database xademo_orders owner xademo;
!xa

=== Add Datasources in Wildfly standalone.xml:

----
				<!-- Only for eedemo-xa -->
                <datasource jndi-name="java:jboss/datasources/customerDataSource" pool-name="customerDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql:xademo_customers</connection-url>
                    <driver>postgres</driver>
                    <security>
                        <user-name>xademo</user-name>
                        <password>ex eh demo?</password>
                    </security>
                </datasource>
				<!-- Only for eedemo-xa -->
                <datasource jndi-name="java:jboss/datasources/ordersDataSource" pool-name="ordersDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql:xademo_orders</connection-url>
                    <driver>postgres</driver>
                    <security>
                        <user-name>xademo</user-name>
                        <password>ex eh demo?</password>
                    </security>
                </datasource>

----

=== Restart WildFly

Using your system's scripts or $WF_HOME/bin/standalone.sh

=== Deploy the app

cd eedemo-xa

mvn wildfly:deploy

=== Run it

Visit http://localhost:8080/eedemo-xa and click the buttons.

=== Verify

psql -d xademo_customers

select * from customer;

\c xademo_orders

select * from order;

=== Clean up

. cd eedemo-xa; mvn wildfly:undeploy
. Stop Wildfly
. psql -c 'drop database xademo_customers'
. psql -c 'drop database xademo_orders'
. Remove the two datasource definitions
. Restart Wildfly

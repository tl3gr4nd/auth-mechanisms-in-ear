# Payara Issue

This repo was created as a reproducer for this ticket

	https://github.com/payara/Payara/issues/

# Description

This project builds an ear which contains

- EJB jar: two custom HttpAuthenticationMechanism implementations
  - CustomHttpAuthenticationMechanism1
  - CustomHttpAuthenticationMechanism2
- WAR: web application 1 with a simple rest resource
  - uses CustomHttpAuthenticationMechanism1
- WAR: web application 2 with a simple rest resource
  - uses CustomHttpAuthenticationMechanism2
    
Defining the to be used authentication mechanism is done as described 
[here](https://docs.payara.fish/community/docs/5.2021.1/documentation/payara-server/server-configuration/security/multiple-mechanism-in-ear.html).

When doing a rest call the configured authentication mechanism should be used.

Urls

- curl -s -v "http://localhost:48080/mywebapp1/api/h/i?user=hans&group=admin"
- curl -s -v "http://localhost:48080/mywebapp2/api/h/i?user=hans&group=admin"

## Expected Outcome

When one of the rest urls is called I expect the configured authentiation mechanism class to be used and 
the validateRequest methods to be called.

The urls return some text.

## Current Outcome

When calling one of the test url's the call intermittently fails. If it fails it usually produces the
following stacktrace in the server log.

    [#|2021-04-13T19:00:26.487+0000|WARNING|Payara 5.2021.1|javax.enterprise.system.container.web.com.sun.web.security|_ThreadID=83;_ThreadName=http-thread-pool::http-listener-1(1);_TimeMillis=1618340426487;_LevelValue=900;|
    JASPIC: http msg authentication fail
    org.jboss.weld.exceptions.AmbiguousResolutionException: WELD-001335: Ambiguous dependencies for type CustomBHttpAuthenticationMechanism with qualifiers
    Possible dependencies:
    - Managed Bean [class my.myejb.CustomBHttpAuthenticationMechanism] with qualifiers [@Any @Default],
    - Managed Bean [class my.myejb.CustomBHttpAuthenticationMechanism] with qualifiers [@Any @Default]
      at org.jboss.weld.bean.builtin.InstanceImpl.checkBeanResolved(InstanceImpl.java:244)
      at org.jboss.weld.bean.builtin.InstanceImpl.get(InstanceImpl.java:113)
      at org.glassfish.soteria.mechanisms.jaspic.HttpBridgeServerAuthModule.validateRequest(HttpBridgeServerAuthModule.java:149)
      at org.glassfish.soteria.mechanisms.jaspic.DefaultServerAuthContext.validateRequest(DefaultServerAuthContext.java:76)
      at com.sun.web.security.realmadapter.JaspicRealm.validateRequest(JaspicRealm.java:391)
      at com.sun.web.security.realmadapter.JaspicRealm.validateRequest(JaspicRealm.java:358)
      at com.sun.web.security.realmadapter.JaspicRealm.validateRequest(JaspicRealm.java:181)
      at com.sun.web.security.RealmAdapter.invokeAuthenticateDelegate(RealmAdapter.java:487)
      at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:468)
      at org.apache.catalina.core.StandardPipeline.doInvoke(StandardPipeline.java:726)
      at org.apache.catalina.core.StandardPipeline.doChainInvoke(StandardPipeline.java:581)
      at com.sun.enterprise.web.WebPipeline.invoke(WebPipeline.java:97)
      at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:158)
      at org.apache.catalina.connector.CoyoteAdapter.doService(CoyoteAdapter.java:371)
      at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:238)
      at com.sun.enterprise.v3.services.impl.ContainerMapper$HttpHandlerCallable.call(ContainerMapper.java:520)
      at com.sun.enterprise.v3.services.impl.ContainerMapper.service(ContainerMapper.java:217)
      at org.glassfish.grizzly.http.server.HttpHandler.runService(HttpHandler.java:182)
      at org.glassfish.grizzly.http.server.HttpHandler.doHandle(HttpHandler.java:156)
      at org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(HttpServerFilter.java:218)
      at org.glassfish.grizzly.filterchain.ExecutorResolver$9.execute(ExecutorResolver.java:95)
      at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeFilter(DefaultFilterChain.java:260)
      at org.glassfish.grizzly.filterchain.DefaultFilterChain.executeChainPart(DefaultFilterChain.java:177)
      at org.glassfish.grizzly.filterchain.DefaultFilterChain.execute(DefaultFilterChain.java:109)
      at org.glassfish.grizzly.filterchain.DefaultFilterChain.process(DefaultFilterChain.java:88)
      at org.glassfish.grizzly.ProcessorExecutor.execute(ProcessorExecutor.java:53)
      at org.glassfish.grizzly.nio.transport.TCPNIOTransport.fireIOEvent(TCPNIOTransport.java:524)
      at org.glassfish.grizzly.strategies.AbstractIOStrategy.fireIOEvent(AbstractIOStrategy.java:89)
      at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.run0(WorkerThreadIOStrategy.java:94)
      at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy.access$100(WorkerThreadIOStrategy.java:33)
      at org.glassfish.grizzly.strategies.WorkerThreadIOStrategy$WorkerThreadRunnable.run(WorkerThreadIOStrategy.java:114)
      at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.doWork(AbstractThreadPool.java:569)
      at org.glassfish.grizzly.threadpool.AbstractThreadPool$Worker.run(AbstractThreadPool.java:549)
      at java.lang.Thread.run(Thread.java:748)

## Steps to reproduce (Only for bug reports)

- install java
- install docker
- install docker-compose
- install maven

Build

    mvn clean install

Start payara with application

    docker-compose up -d

Wait until payara is booted up, so wait until logging stops

    docker-compose logs --follow

Check REST API works

    curl -s -v "http://localhost:48080/mywebapp1/api/h/i?user=hans&group=admin"

- if success response code is 200 otherwise 500

If it failed check the logs

    docker-compose logs

## Steps to reproduce the intermittent behaviour

Run:

    ./test-loop.sh

This script 

- recreates the docker container from scratch
- deploys the ear
- does this in a loop for 50 times

Using this script you can see if it consistently produces an error.
You will find it does not. Sometimes it works and you get 200, sometimes 500 with the above stacktrace.

## Environment ##

- **Payara Version**: 5.2021.1
- **Edition**: Full
- **JDK Version**: 1.8.0_275 OpenJDK
- **Operating System**: Mac
- **Database**: n/a
